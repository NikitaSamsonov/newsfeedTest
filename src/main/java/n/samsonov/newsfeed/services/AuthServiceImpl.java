package n.samsonov.newsfeed.services;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.AuthUserDto;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.LoginUserDto;
import n.samsonov.newsfeed.dto.RegisterUserDto;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.errors.CustomException;
import n.samsonov.newsfeed.errors.ErrorEnum;
import n.samsonov.newsfeed.mapper.UserMapper;
import n.samsonov.newsfeed.repository.UserRepository;
import n.samsonov.newsfeed.security.JwtProvider;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static n.samsonov.newsfeed.services.FileServiceImpl.loadFile;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtToken;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomSuccessResponse<LoginUserDto> registerUser(RegisterUserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException(ErrorEnum.USER_ALREADY_EXISTS);
        }
        UserEntity entity = UserMapper.INSTANCE.registerUserDtoToUserEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setAvatar(""  + loadFile);
        userRepository.save(entity);
        LoginUserDto userLoginDto = UserMapper.INSTANCE.userEntityToLoginUserDto(entity);
        userLoginDto.setToken(jwtToken.buildToken(entity.getId()));
        return CustomSuccessResponse.okWithData(userLoginDto);
    }

    @Override
    public CustomSuccessResponse authUser(AuthUserDto dto) {
        UserEntity entity = userRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
               new CustomException(ErrorEnum.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new CustomException(ErrorEnum.PASSWORD_NOT_VALID);
        }
        LoginUserDto userLoginDto = UserMapper.INSTANCE.userEntityToLoginUserDto(entity);
        userLoginDto.setToken(jwtToken.buildToken(entity.getId()));
        return CustomSuccessResponse.okWithData(userLoginDto);
    }
}
