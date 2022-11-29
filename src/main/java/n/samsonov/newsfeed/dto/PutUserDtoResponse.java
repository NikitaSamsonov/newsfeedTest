package n.samsonov.newsfeed.dto;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PutUserDtoResponse {
    private UUID id;
    private String email;
    private String name;
    private String role;
    private String avatar;
}
