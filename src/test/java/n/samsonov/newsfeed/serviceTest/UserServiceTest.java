package n.samsonov.newsfeed.serviceTest;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanBase(){
        userRepository.deleteAll();
    }

    @Test
    public void getAllUserTests(){

        var user1 = new UserEntity()
                .setAvatar("imposter")
                .setEmail("fucktests@sdness.com")
                .setName("tester")
                .setRole("user")
                .setPassword("123456");

        var user2 = new UserEntity()
                .setAvatar("aboba")
                .setEmail("ggwpg@sdness.com")
                .setName("nikita")
                .setRole("user")
                .setPassword("123456");

        userRepository.save(user1);
        userRepository.save(user2);

        List<PublicUserView> userViewList = userRepository.viewAllUsers();

        assertEquals(2 , userViewList.size());
        assertEquals(userViewList.get(0).getName(), "tester");


        var expectResponse = CustomSuccessResponse
                                                      .okWithData(userViewList);
        assertThat(expectResponse.getData()).isNotNull();
        assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode" , 1);

        cleanBase();
    }


    @Test
    public void getUserInfoByIdTest(){

        UserEntity entity = new UserEntity()
                .setEmail("hatetest@sdness.com")
                .setName("aboba")
                .setRole("user")
                .setAvatar("string");

        userRepository.save(entity);

        PublicUserView userInfo = userRepository.viewUserById(entity.getId());

        assertThat(userInfo)
                .hasFieldOrPropertyWithValue("email" ,entity.getEmail())
                .hasFieldOrPropertyWithValue("name", entity.getName())
                .hasFieldOrPropertyWithValue("role", entity.getRole())
                .hasFieldOrPropertyWithValue("avatar", entity.getAvatar());

        var expectResponse = CustomSuccessResponse.okWithData(userInfo);
        assertThat(expectResponse).isNotNull();
        assertThat(expectResponse.getData()).isNotNull();
        assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode" , 1);

        cleanBase();

    }

    @Test
    @Transactional
    public void replaceUserInfoTest() {

        UserEntity entity = new UserEntity()
                .setEmail("auaua@gmail.com")
                .setName("imposter")
                .setRole("user")
                .setAvatar("tester");
        userRepository.save(entity);

        PutUserDto putUserDto = new PutUserDto()
                .setEmail("hatetest@sdness.com")
                .setName("aboba")
                .setRole("user")
                .setAvatar("string");

        userRepository.updateUserInfo(
                putUserDto.getEmail(),
                putUserDto.getName(),
                putUserDto.getAvatar(),
                putUserDto .getRole(), entity.getId());

        PutUserDtoResponse putUserDtoResponse = UserMapper.INSTANCE
                                  .putUserDtoToPutUserDtoResponse(putUserDto);
        var updatedUser = userRepository.findByName(putUserDto.getName());

       assertThat(updatedUser)
               .hasFieldOrPropertyWithValue("name" ,putUserDto.getName())
               .hasFieldOrPropertyWithValue("email", putUserDto.getEmail())
               .hasFieldOrPropertyWithValue("role", putUserDto.getRole())
               .hasFieldOrPropertyWithValue("avatar", putUserDto.getAvatar());

        var expectResponse = CustomSuccessResponse
                                  .okWithData(putUserDtoResponse);

        assertThat(expectResponse).isNotNull();
        assertThat(expectResponse.getData()).isNotNull();
        assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);

       //cleanBase();

    }

    @Test
    public void exeptionsUserReplaceTest() throws CustomException{

        UserEntity entity = new UserEntity().setEmail("wrong@email.com");
        userRepository.save(entity);

        Assertions.assertThrows(CustomException.class,
                () -> {
                    var entity1 = userRepository.findById(UUID.randomUUID());
                    if (entity1.isEmpty()) {
                        throw new CustomException(ErrorEnum.USER_NOT_FOUND);
                    }
                });
        Assertions.assertEquals(5, ErrorEnum.USER_NOT_FOUND.getCode());

        Assertions.assertThrows(CustomException.class,
                () -> {
                    var entity2 = userRepository.findByEmail("wrong@email.com");
                    if (entity2.isPresent()) {
                        throw new CustomException(ErrorEnum.USER_NOT_FOUND);
                    }
                });
        Assertions.assertEquals(46, ErrorEnum.USER_WITH_THIS_EMAIL_ALREADY_EXIST.getCode());
    }

    @Test
    @Transactional
    public void deleteUserTest(){

        UserEntity user = new UserEntity();
        userRepository.save(user);

        userRepository.deleteById(user.getId());


        var expectResponse = BaseSuccessResponse.common();
        assertThat(expectResponse).isNotNull();
        assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);
    }

}
