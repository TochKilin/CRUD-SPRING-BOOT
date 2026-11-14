package myspring.com.myspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private int numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;

    // Helper Method សម្រាប់ Map ពី Page<Entity> ទៅ PageResponse<DTO> ដោយផ្អែកលើ Mapper Function
    public static <E, D> PageResponse<D> mapFrom(Page<E> page, Function<E, D> mapper) {
        List<D> content = page.getContent().stream()
                .map(mapper)
                .toList();

        return PageResponse.<D>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .numberOfElements(page.getNumberOfElements())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();
    }
}