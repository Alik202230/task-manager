package am.itspace.task.manager.service;

import am.itspace.task.manager.dto.CreateTaskRequest;
import am.itspace.task.manager.dto.CreateTaskResponse;
import am.itspace.task.manager.dto.PageResponse;
import am.itspace.task.manager.dto.TaskResponse;
import am.itspace.task.manager.dto.UpdateTaskRequest;
import am.itspace.task.manager.dto.UpdatedTaskResponse;
import am.itspace.task.manager.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

  CreateTaskResponse create(CreateTaskRequest request);

  PageResponse<TaskResponse> getAllTaskWithPage(int page, int size);

  UpdatedTaskResponse updateTask(UpdateTaskRequest request, Long id);

  List<TaskResponse> getAllTasks();

  TaskResponse getTaskById(Long id);

  void deleteTaskById(Long id);

}
