package am.itspace.task.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

  private List<TaskResponse> content;
  private int number;
  private int size;
  private long totalElements;
  private int totalPages;
  private boolean first;
  private boolean last;
  private boolean hasNext;
  private boolean hasPrevious;

  public PageResponse(List<TaskResponse> content, int number, int size, long totalElements, int totalPages) {
    this.content = content;
    this.number = number;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }
}
