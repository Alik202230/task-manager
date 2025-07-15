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
import am.itspace.task.manager.service.TaskService;
import am.itspace.task.manager.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  @Override
  public CreateTaskResponse create(CreateTaskRequest request) {

    log.info("Task creation is in progress");

    Task task = Task.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .isCompleted(request.getIsCompleted())
        .createdAt(LocalDateTime.now())
        .build();

    log.info("Task with id {} is created successfully", task.getId());

    Task savedTask = this.taskRepository.save(task);

    return TaskMapper.mapToCreateTaskResponse(savedTask);
  }

  @Override
  public PageResponse<TaskResponse> getAllTasks(int page, int size) {

    Pageable pageable = PageUtil.createPage(page, size);

    Page<Task> taskPage = this.taskRepository.findAll(pageable);

    Function<Task, TaskResponse> taskTaskResponseFunction = task -> TaskMapper.toTaskResponse(task);

//    List<TaskResponse> tasks = taskPage.stream()
//        .map(t -> TaskMapper.toTaskResponse(t))
//        .toList();

    return PageMapper.toPageWrapper(taskPage, taskTaskResponseFunction);
  }

  @Override
  public UpdatedTaskResponse updateTask(UpdateTaskRequest request, Long id) {
    Optional<Task> optionalTask = this.taskRepository.findById(id);

    if (optionalTask.isEmpty()) {
      log.error("Task not found with id {}", id);
      throw new TaskNotFoundException("Task with " + id + " is not presented");
    }

    Task task = new Task();
    task.setTitle(request.getTitle());
    task.setDescription(request.getDescription());

    Task updatedTask = this.taskRepository.save(task);

    log.info("Task with id {} updated and save to the database", id);

    return TaskMapper.toUpdatedTaskResponse(updatedTask);

  }

  @Override
  public Optional<TaskResponse> getTaskById(Long id) {
    return Optional.ofNullable(this.taskRepository.findById(id)
        .map(task -> TaskMapper.toTaskResponse(task))
        .orElseThrow(() -> {
          log.error("task with id {} not found", id);
          return new TaskNotFoundException("Task with id " + id + " not found");
        }));
  }

  @Override
  public void deleteTaskById(Long id) {
    this.taskRepository.findById(id)
        .ifPresentOrElse(task -> taskRepository.delete(task),
            () -> {
              log.error("Task with id {} is not found", id);
              throw new TaskNotFoundException("Task with " + id + " not found");
            });
  }
}
