package am.itspace.task.manager.mapper;

import am.itspace.task.manager.dto.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public final class PageMapper {

  private PageMapper() {}

  public static <T, R> PageResponse<R> toPageWrapper(Page<T> page, Function<T, R> converter) {
    List<R> content = page.getContent().stream()
        .map(converter)
        .toList();

    return PageResponse.<R>builder()
        .content(content)
        .number(page.getNumber())
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .first(page.isFirst())
        .last(page.isLast())
        .hasNext(page.hasNext())
        .hasPrevious(page.hasPrevious())
        .build();
  }

}
