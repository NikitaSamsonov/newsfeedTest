package n.samsonov.newsfeed.dto;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class LoginUserDto {
    private UUID id;
    private String email;
    private String name;
    private String role;
    private String avatar;
    private String token;
}
