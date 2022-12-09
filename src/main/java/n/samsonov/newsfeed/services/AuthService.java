package n.samsonov.newsfeed.services;

import n.samsonov.newsfeed.dto.AuthUserDto;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.LoginUserDto;
import n.samsonov.newsfeed.dto.RegisterUserDto;

public interface AuthService {

    LoginUserDto registerUser(RegisterUserDto dto);

    CustomSuccessResponse authUser(AuthUserDto dto);
}
