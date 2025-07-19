package am.itspace.task.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskResponse {
  private Long id;
  private String title;
  private String description;
  private Boolean isCompleted;
  private LocalDateTime createdAt;

  public static CreateTaskResponseBuilder builder() {
    return new CreateTaskResponseBuilder();
  }

  public static class CreateTaskResponseBuilder {
    private Long id;
    private String title;
    private String description;
    private Boolean isCompleted;
    private LocalDateTime createdAt;

    public CreateTaskResponseBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public CreateTaskResponseBuilder title(String title) {
      this.title = title;
      return this;
    }

    public CreateTaskResponseBuilder description(String description) {
      this.description = description;
      return this;
    }

    public CreateTaskResponseBuilder isCompleted(Boolean isCompleted) {
      this.isCompleted = isCompleted;
      return this;
    }

    public CreateTaskResponseBuilder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public CreateTaskResponse build() {
      return new CreateTaskResponse(this.id, this.title, this.description, this.isCompleted, this.createdAt);
    }

    public String toString() {
      return "CreateTaskResponse.CreateTaskResponseBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", isCompleted=" + this.isCompleted + ", createdAt=" + this.createdAt + ")";
    }
  }
}
