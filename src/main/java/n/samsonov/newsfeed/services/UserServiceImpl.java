package n.samsonov.newsfeed.services;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.BaseSuccessResponse;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.PutUserDto;
import n.samsonov.newsfeed.dto.PutUserDtoResponse;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.errors.CustomException;
import n.samsonov.newsfeed.errors.ErrorEnum;
import n.samsonov.newsfeed.mapper.UserMapper;
import n.samsonov.newsfeed.repository.PublicUserView;
import n.samsonov.newsfeed.repository.UserRepository;

import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public CustomSuccessResponse viewAllUsers() {
        List<PublicUserView> userViewList = userRepository.viewAllUsers();
        return CustomSuccessResponse.okWithData(userViewList);
    }

    @Override
    public CustomSuccessResponse getUserInfoById(UUID id) {
        PublicUserView userInfo = userRepository.viewUserById(id);
        return CustomSuccessResponse.okWithData(userInfo);
    }

    @Transactional
    @Override
    public CustomSuccessResponse replaceUserInfo(PutUserDto dto, UUID id, String email) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));
        if (userRepository.findByEmail(dto.getEmail()).isPresent() && !dto.getEmail().equals(email)) {
            throw new CustomException(ErrorEnum.USER_WITH_THIS_EMAIL_ALREADY_EXIST);
        }
        userRepository.updateUserInfo(dto.getEmail(), dto.getName(), dto.getAvatar(), dto.getRole(), id);
        PutUserDtoResponse putUserDtoResponse = UserMapper.INSTANCE.putUserDtoToPutUserDtoResponse(dto);
        putUserDtoResponse.setId(userEntity.getId());
        return CustomSuccessResponse.okWithData(putUserDtoResponse);
    }


    @Transactional
    @Override
    public BaseSuccessResponse deleteUser(UUID id) {
        userRepository.deleteById(id);
        return BaseSuccessResponse.common();
    }
}

