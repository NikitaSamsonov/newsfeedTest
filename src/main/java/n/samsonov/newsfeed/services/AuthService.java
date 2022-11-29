package n.samsonov.newsfeed.services;

import n.samsonov.newsfeed.dto.AuthUserDto;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.RegisterUserDto;

public interface AuthService {

    CustomSuccessResponse registerUser(RegisterUserDto dto);

    CustomSuccessResponse authUser(AuthUserDto dto);
}
