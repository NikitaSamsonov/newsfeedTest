package n.samsonov.newsfeed.serviceTest;

import n.samsonov.newsfeed.TestConfig;
import n.samsonov.newsfeed.dto.AuthUserDto;
import n.samsonov.newsfeed.dto.RegisterUserDto;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.repository.NewsRepository;
import n.samsonov.newsfeed.repository.TagsRepository;
import n.samsonov.newsfeed.repository.UserRepository;
import n.samsonov.newsfeed.services.AuthService;
import n.samsonov.newsfeed.services.UserService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserEntity user = new UserEntity().setName("ponyal");

    @BeforeEach
    void cleanBase(){
        userRepository.deleteAll();
    }

    @Test
    public void registerUserTest(){

        var user = new RegisterUserDto()
                .setAvatar("imposter")
                .setEmail("fucktests@sdness.com")
                .setName("tester")
                .setRole("user")
                .setPassword("123456");

        var expectOutput = authService.registerUser(user);

        AssertionsForClassTypes.assertThat(expectOutput).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput.getData()).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);

        var expectSave =  userRepository.existsByEmail("fucktests@sdness.com");
        Assertions.assertTrue(expectSave);

    }

    @Test
    public void authUserTest() {

        var authUserDto = new AuthUserDto()
                .setEmail("fucktests@sdness.com")
                .setPassword("123456");

        var user1 = new UserEntity()
                .setAvatar("imposter")
                .setEmail("fucktests@sdness.com")
                .setName("tester")
                .setRole("user");
        user1.setPassword(passwordEncoder.encode(authUserDto.getPassword()));
        userRepository.save(user1);

        var expectOutput = authService.authUser(authUserDto);

        AssertionsForClassTypes.assertThat(expectOutput).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput.getData()).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);
    }
}