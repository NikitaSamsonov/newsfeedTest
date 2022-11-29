package n.samsonov.newsfeed.security;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.errors.CustomException;
import n.samsonov.newsfeed.errors.ErrorEnum;
import n.samsonov.newsfeed.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String id) throws CustomException {
        UserEntity userEntity = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));
        return UserDetailsImpl.userEntityToUserDetailsImpl(userEntity);
    }
}
