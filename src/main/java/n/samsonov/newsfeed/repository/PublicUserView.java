package n.samsonov.newsfeed.repository;

import java.util.UUID;

import lombok.experimental.Accessors;

@Accessors(chain = true)
public interface PublicUserView {
    String getAvatar();
    String getEmail();
    UUID getId();
    String getName();
    String getRole();

    void setAvatar(String avatar);
    void setEmail(String email);
    void setId(UUID id);
    void setName(String name);
    void setRole(String role);





}
