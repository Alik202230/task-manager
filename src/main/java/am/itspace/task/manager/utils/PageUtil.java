package am.itspace.task.manager.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageUtil {

  public static Pageable createPage(int page, int size) {
    return PageRequest.of(page, size, Sort.by("id").ascending());
  }
}
