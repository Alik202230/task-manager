package am.itspace.task.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

  private List<T> content;
  private int number;
  private int size;
  private long totalElements;
  private int totalPages;
  private boolean first;
  private boolean last;
  private boolean hasNext;
  private boolean hasPrevious;

  public PageResponse(List<T> content, int number, int size, long totalElements, int totalPages) {
    this.content = content;
    this.number = number;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }
}
