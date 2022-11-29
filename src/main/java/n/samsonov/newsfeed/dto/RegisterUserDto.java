package n.samsonov.newsfeed.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import n.samsonov.newsfeed.errors.ValidationConstants;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RegisterUserDto {

    @NotBlank(message = ValidationConstants.USER_AVATAR_NOT_NULL)
    private String avatar;

    @NotBlank(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    @Email(regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}\\.[a-z]{2,}$",
            message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @Length(min = 3, max = 100, message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    private String email;

    @NotBlank(message = ValidationConstants.USER_NAME_HAS_TO_BE_PRESENT)
    @Length(min = 3, max = 25, message = ValidationConstants.USERNAME_SIZE_NOT_VALID)
    private String name;

    @NotBlank(message = ValidationConstants.USER_ROLE_NOT_NULL)
    @Length(min = 3, max = 25, message = ValidationConstants.ROLE_SIZE_NOT_VALID)
    private String role;

    @NotBlank(message = ValidationConstants.PASSWORD_NOT_NULL)
    @Length(min = 5, max = 25, message = ValidationConstants.PASSWORD_NOT_VALID)
    private String password;
}
