package n.samsonov.newsfeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import n.samsonov.newsfeed.errors.ErrorEnum;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSuccessResponse<T> {

    private T data;
    private Integer statusCode;
    private String exeptionCode;
    private Boolean success;
    private Integer error;
    private T codes;

    public static <T> CustomSuccessResponse<T> okWithData(T data) {
        return new CustomSuccessResponse<T>()
                .setData(data)
                .setSuccess(true)
                .setStatusCode(1);
    }

    public static <T> CustomSuccessResponse<T> handleNullException(String exCode) {
        return new CustomSuccessResponse<T>()
                .setSuccess(true)
                .setStatusCode(ErrorEnum.getCodes(exCode).getCode())
                .setCodes((T) new Integer[]{ErrorEnum.getCodes(exCode).getCode()});

    }

    public static <T> CustomSuccessResponse<T> handleException(Integer[] error) {
        return new CustomSuccessResponse<T>()
                .setStatusCode(error[0])
                .setSuccess(true)
                .setCodes((T) error);
    }
}
