package am.itspace.task.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedTaskResponse {

  private String title;
  private String description;
  private LocalDateTime updatedAt;

}
