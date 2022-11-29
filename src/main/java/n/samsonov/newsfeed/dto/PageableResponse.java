package n.samsonov.newsfeed.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PageableResponse<T> {
    private T content;
    private Integer numberOfElements;
}
