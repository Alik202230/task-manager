package am.itspace.task.manager.mapper;

import am.itspace.task.manager.dto.CreateTaskResponse;
import am.itspace.task.manager.dto.TaskResponse;
import am.itspace.task.manager.dto.UpdatedTaskResponse;
import am.itspace.task.manager.entity.Task;

public final class TaskMapper {

  private TaskMapper() {}

  public static CreateTaskResponse mapToCreateTaskResponse(Task task) {
    return CreateTaskResponse.builder()
        .id(task.getId())
        .title(task.getTitle())
        .description(task.getDescription())
        .isCompleted(task.getIsCompleted())
        .createdAt(task.getCreatedAt())
        .build();

  }

  public static TaskResponse toTaskResponse(Task task) {
    return TaskResponse.builder()
        .title(task.getTitle())
        .description(task.getDescription())
        .isCompleted(task.getIsCompleted())
        .build();
  }

  public static UpdatedTaskResponse toUpdatedTaskResponse(Task task) {
    return UpdatedTaskResponse.builder()
        .title(task.getTitle())
        .description(task.getDescription())
        .updatedAt(task.getUpdatedAt())
        .build();
  }

}
