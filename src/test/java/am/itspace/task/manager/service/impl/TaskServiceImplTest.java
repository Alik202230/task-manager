package am.itspace.task.manager.service.impl;

import am.itspace.task.manager.dto.CreateTaskRequest;
import am.itspace.task.manager.dto.CreateTaskResponse;
import am.itspace.task.manager.dto.PageResponse;
import am.itspace.task.manager.dto.TaskResponse;
import am.itspace.task.manager.dto.UpdateTaskRequest;
import am.itspace.task.manager.dto.UpdatedTaskResponse;
import am.itspace.task.manager.entity.Task;
import am.itspace.task.manager.exception.TaskNotFoundException;
import am.itspace.task.manager.mapper.PageMapper;
import am.itspace.task.manager.mapper.TaskMapper;
import am.itspace.task.manager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @Mock
  public TaskRepository taskRepository;

  @InjectMocks
  public TaskServiceImpl taskService;

  private CreateTaskRequest createTaskRequest;
  private Task taskToSave;
  private Task savedTask;
  private CreateTaskResponse expectedResponse;

  @BeforeEach
  void setUp() {
    createTaskRequest = CreateTaskRequest.builder()
        .title("task title")
        .description("task description")
        .isCompleted(false)
        .build();

    taskToSave = Task.builder()
        .title("save task title")
        .description("save task description")
        .isCompleted(false)
        .build();

    savedTask = Task.builder()
        .id(1L) // Simulate an ID assigned by the database
        .title("Test Task")
        .description("This is a test description.")
        .isCompleted(false)
        .createdAt(LocalDateTime.now()) // Simulate a created date
        .build();

    // Create the expected response from the mapper
    expectedResponse = CreateTaskResponse.builder()
        .id(1L)
        .title("Test Task")
        .description("This is a test description.")
        .isCompleted(false)
        .createdAt(savedTask.getCreatedAt())
        .build();

  }

  @Test
  void create() {

    when(taskRepository.save(Mockito.any(Task.class))).thenReturn(savedTask);

    try (MockedStatic<TaskMapper> mockedTaskMapper = mockStatic(TaskMapper.class)) {
      mockedTaskMapper.when(() -> TaskMapper.mapToCreateTaskResponse(savedTask))
          .thenReturn(expectedResponse);

      CreateTaskResponse actualResponse = taskService.create(createTaskRequest);

      verify(taskRepository, times(1)).save(Mockito.any(Task.class));

      mockedTaskMapper.verify(() -> TaskMapper.mapToCreateTaskResponse(savedTask), times(1));

      assertNotNull(actualResponse, "The response should not be null");
      assertEquals(expectedResponse.getId(), actualResponse.getId(), "IDs should match");
      assertEquals(expectedResponse.getTitle(), actualResponse.getTitle(), "Titles should match");
      assertEquals(expectedResponse.getDescription(), actualResponse.getDescription(), "Descriptions should match");
      assertEquals(expectedResponse.getIsCompleted(), actualResponse.getIsCompleted(), "Completion status should match");
      assertEquals(expectedResponse.getCreatedAt(), actualResponse.getCreatedAt(), "Creation dates should match");
    }
  }

  @Test
  void getAllTasks_success() {
    int page = 0;
    int size = 10;
    Pageable pageable = PageRequest.of(page, size);
    List<Task> taskList = List.of(savedTask);
    Page<Task> taskPage = new PageImpl<>(taskList, pageable, taskList.size());

    List<TaskResponse> taskResponses = List.of(
        TaskResponse.builder()
            .title(savedTask.getTitle())
            .description(savedTask.getDescription())
            .isCompleted(savedTask.getIsCompleted())
            .build()
    );

    PageResponse<TaskResponse> expectedPageResponse = new PageResponse<>(
        taskResponses,
        taskPage.getNumber(),
        taskPage.getSize(),
        taskPage.getTotalElements(),
        taskPage.getTotalPages()
    );

    when(taskRepository.findAll(Mockito.any(Pageable.class))).thenReturn(taskPage);

    try (MockedStatic<TaskMapper> mockedTaskMapper = mockStatic(TaskMapper.class);
         MockedStatic<PageMapper> mockedPageMapper = mockStatic(PageMapper.class)) {

      mockedTaskMapper.when(() -> TaskMapper.toTaskResponse(Mockito.any(Task.class)))
          .thenReturn(taskResponses.get(0));
      mockedPageMapper.when(() -> PageMapper.toPageWrapper(eq(taskPage), Mockito.any()))
          .thenReturn(expectedPageResponse);


      PageResponse<TaskResponse> result = taskService.getAllTasks(page, size);


      assertNotNull(result);
      assertEquals(expectedPageResponse.getContent().size(), result.getContent().size());
      assertEquals(expectedPageResponse.getTotalElements(), result.getTotalElements());
      verify(taskRepository).findAll(Mockito.any(Pageable.class));
    }
  }

  @Test
  void updateTask_success() {
    Long taskId = 1L;
    UpdateTaskRequest updateRequest = new UpdateTaskRequest("Updated Title", "Updated Description");
    Task existingTask = new Task();
    existingTask.setId(taskId);

    Task updatedTask = new Task();
    updatedTask.setId(taskId);
    updatedTask.setTitle(updateRequest.getTitle());
    updatedTask.setDescription(updateRequest.getDescription());

    UpdatedTaskResponse expectedResponse = UpdatedTaskResponse.builder()
        .title(updateRequest.getTitle())
        .description(updateRequest.getDescription())
        .build();

    when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
    when(taskRepository.save(Mockito.any(Task.class))).thenReturn(updatedTask);

    try (MockedStatic<TaskMapper> mockedTaskMapper = mockStatic(TaskMapper.class)) {
      mockedTaskMapper.when(() -> TaskMapper.toUpdatedTaskResponse(Mockito.any(Task.class)))
          .thenReturn(expectedResponse);

      UpdatedTaskResponse result = taskService.updateTask(updateRequest, taskId);

      assertNotNull(result);
      assertEquals(expectedResponse.getTitle(), result.getTitle());
      assertEquals(expectedResponse.getDescription(), result.getDescription());
    }
  }

  @Test
  void updateTask_throwsNotFoundException() {
    Long taskId = 1L;
    UpdateTaskRequest updateRequest = new UpdateTaskRequest("Title", "Description");
    when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

    assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(updateRequest, taskId));
  }

  @Test
  void getTaskById_success() {
    Long taskId = 1L;
    Task task = new Task();
    task.setId(taskId);
    TaskResponse expectedResponse = TaskResponse.builder()
        .title("Test Task")
        .description("Test Description")
        .build();

    when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

    try (MockedStatic<TaskMapper> mockedTaskMapper = mockStatic(TaskMapper.class)) {
      mockedTaskMapper.when(() -> TaskMapper.toTaskResponse(task))
          .thenReturn(expectedResponse);

      Optional<TaskResponse> result = taskService.getTaskById(taskId);

      assertTrue(result.isPresent());
    }
  }

  @Test
  void getTaskById_notFound() {
    Long taskId = 1L;
    when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

    assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
  }

  @Test
  void deleteTaskById_success() {
    Long taskId = 1L;
    Task task = new Task();
    task.setId(taskId);
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

    taskService.deleteTaskById(taskId);

    verify(taskRepository).delete(task);
  }

  @Test
  void deleteTaskById_notFound() {
    Long taskId = 1L;
    when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

    taskService.deleteTaskById(taskId);
    verify(taskRepository, never()).delete(Mockito.any());
  }

}