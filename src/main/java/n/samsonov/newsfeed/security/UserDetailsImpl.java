package n.samsonov.newsfeed.security;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import lombok.Getter;
import n.samsonov.newsfeed.entity.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserDetailsImpl implements UserDetails {

    private UUID id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authority;

    public static UserDetailsImpl userEntityToUserDetailsImpl(UserEntity userEntity) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.id = userEntity.getId();
        userDetails.email = userEntity.getEmail();
        userDetails.password = userEntity.getPassword();
        userDetails.authority = Collections.singletonList(new SimpleGrantedAuthority(userEntity.getEmail()));
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UUID getId() {
        return id;
    }
}
