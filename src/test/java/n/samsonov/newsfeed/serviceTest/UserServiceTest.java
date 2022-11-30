package n.samsonov.newsfeed.serviceTest;

import n.samsonov.newsfeed.TestConfig;
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
import n.samsonov.newsfeed.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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

        var expectOutput = userService.viewAllUsers();

        assertThat(expectOutput.getData()).isNotNull();
        assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode" , 1);

    }


    @Test
    public void getUserInfoByIdTest(){

        UserEntity entity = new UserEntity()
                .setEmail("hatetest@sdness.com")
                .setName("aboba")
                .setRole("user")
                .setAvatar("string");
        userRepository.save(entity);

        var expectOutput = userService.getUserInfoById(entity.getId());

        PublicUserView userInfo = userRepository.viewUserById(entity.getId());

        assertThat(expectOutput).isNotNull();
        assertThat(expectOutput.getData()).isNotNull();
        assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode" , 1);
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

        var expectOutput = userService.replaceUserInfo(putUserDto,entity.getId(),"auaua@gmail.com");

        assertThat(expectOutput).isNotNull();
        assertThat(expectOutput.getData()).isNotNull();
        assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);


        var changedNews = userRepository.getById(entity.getId());

        assertThat(changedNews)
                .hasFieldOrPropertyWithValue("email", putUserDto.getEmail())
                .hasFieldOrPropertyWithValue("name", putUserDto.getName())
                .hasFieldOrPropertyWithValue("role", putUserDto.getRole())
                .hasFieldOrPropertyWithValue("avatar", putUserDto.getAvatar());

    }


    @Test
    @Transactional
    public void deleteUserTest(){

        UserEntity user = new UserEntity();
        userRepository.save(user);

        var expectOutput = userService.deleteUser(user.getId());

        assertThat(expectOutput).isNotNull();
        assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);
    }

}
