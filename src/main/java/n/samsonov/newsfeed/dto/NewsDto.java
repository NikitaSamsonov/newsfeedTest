package n.samsonov.newsfeed.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import n.samsonov.newsfeed.errors.ValidationConstants;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NewsDto {

    @Length(min = 3, max = 160, message = ValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID)
    @NotNull(message = ValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT)
    String description;

    @NotBlank(message = ValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT)
    String image;

    @NotEmpty(message = ValidationConstants.TAGS_NOT_VALID)
    List<String> tags;

    @NotNull(message = ValidationConstants.NEWS_TITLE_NOT_NULL)
    @Length(min = 3, max = 160, message = ValidationConstants.NEWS_TITLE_SIZE)
    String title;
}
