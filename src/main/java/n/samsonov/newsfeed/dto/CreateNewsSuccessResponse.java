package n.samsonov.newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateNewsSuccessResponse {
    private Long id;
    private Integer statusCode;
    private Boolean success;

    public static CreateNewsSuccessResponse okWithId(Long id) {
        return new CreateNewsSuccessResponse()
                .setId(id)
                .setSuccess(true)
                .setStatusCode(1);
        }
}

