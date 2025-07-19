package am.itspace.task.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedTaskResponse {

  private String title;
  private String description;
  private LocalDateTime updatedAt;

  public static UpdatedTaskResponse build() {
    return new UpdatedTaskResponse();
  }

  public static class UpdatedTaskResponseBuilder {
    private String title;
    private String description;
    private LocalDateTime updatedAt;


    public UpdatedTaskResponseBuilder title(String title) {
      this.title = title;
      return this;
    }

    public UpdatedTaskResponseBuilder description(String description) {
      this.description = description;
      return this;
    }

    public UpdatedTaskResponseBuilder updatedAt(LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public UpdatedTaskResponse build() {
      return new UpdatedTaskResponse(this.title, this.description, this.updatedAt);
    }

  }


}
