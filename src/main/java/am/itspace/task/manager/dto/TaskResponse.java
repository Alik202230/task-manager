package am.itspace.task.manager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
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

  public static TaskResponseBuilder builder() {
    return new TaskResponseBuilder();
  }


  public static class TaskResponseBuilder {
    private String title;
    private String description;
    private Boolean isCompleted;

    TaskResponseBuilder() {
    }

    public TaskResponseBuilder title(String title) {
      this.title = title;
      return this;
    }

    public TaskResponseBuilder description(String description) {
      this.description = description;
      return this;
    }

    public TaskResponseBuilder isCompleted(Boolean isCompleted) {
      this.isCompleted = isCompleted;
      return this;
    }

    public TaskResponse build() {
      return new TaskResponse(this.title, this.description, this.isCompleted);
    }

    public String toString() {
      return "TaskResponse.TaskResponseBuilder(title=" + this.title + ", description=" + this.description + ", isCompleted=" + this.isCompleted + ")";
    }
  }
}
