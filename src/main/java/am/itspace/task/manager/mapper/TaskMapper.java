package am.itspace.task.manager.mapper;

import am.itspace.task.manager.dto.CreateTaskResponse;
import am.itspace.task.manager.dto.TaskResponse;
import am.itspace.task.manager.dto.UpdatedTaskResponse;
import am.itspace.task.manager.entity.Task;

public final class TaskMapper {
  
  public static CreateTaskResponse mapToCreateTaskResponse(Task task) {
    return new CreateTaskResponse(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getIsCompleted(),
        task.getCreatedAt()
    );
  }

  public static TaskResponse toTaskResponse(Task task) {
    return new TaskResponse(
        task.getTitle(),
        task.getDescription(),
        task.getIsCompleted()
    );
  }

  public static UpdatedTaskResponse toUpdatedTaskResponse(Task task) {
    return new UpdatedTaskResponse(
        task.getTitle(),
        task.getDescription(),
        task.getUpdatedAt()
    );
  }

}
