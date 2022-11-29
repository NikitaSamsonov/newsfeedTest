package n.samsonov.newsfeed.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import n.samsonov.newsfeed.errors.ValidationConstants;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class AuthUserDto {

    @NotBlank(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    @Email(regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}\\.[a-z]{2,}$",
          message = ValidationConstants.USER_EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = ValidationConstants.PASSWORD_NOT_VALID)
    private String password;
}
