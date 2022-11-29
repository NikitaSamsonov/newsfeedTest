package n.samsonov.newsfeed.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import n.samsonov.newsfeed.entity.TagsEntity;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GetNewsOutDto {
    String description;
    Long id;
    String image;
    String title;
    List<TagsEntity> tags;
    UUID userId;
    String username;
}
