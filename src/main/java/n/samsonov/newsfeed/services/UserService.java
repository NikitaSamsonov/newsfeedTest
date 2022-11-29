package n.samsonov.newsfeed.services;
import java.util.UUID;

import n.samsonov.newsfeed.dto.BaseSuccessResponse;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.PutUserDto;
;
public interface UserService {

    CustomSuccessResponse viewAllUsers();

    CustomSuccessResponse getUserInfoById(UUID id);

    CustomSuccessResponse replaceUserInfo(PutUserDto dto, UUID id,String email);

    BaseSuccessResponse deleteUser(UUID id);
}
