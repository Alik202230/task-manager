package am.itspace.task.manager.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

@Data
@Builder
@NoArgsConstructor
public class TaskResponse {

  private String title;
  private String description;
  private Boolean isCompleted;

  public TaskResponse(String title, String description, Boolean isCompleted) {
    this.title = title;
    this.description = description;
    this.isCompleted = isCompleted;
  }

}
