package n.samsonov.newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseSuccessResponse<T> {
    private Integer statusCode;
    private Boolean success;

    public static BaseSuccessResponse common() {
        return new BaseSuccessResponse()
                .setSuccess(true)
                .setStatusCode(1);
    }
}
