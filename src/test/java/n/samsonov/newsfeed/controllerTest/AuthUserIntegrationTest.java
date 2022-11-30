package n.samsonov.newsfeed.controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import n.samsonov.newsfeed.controllers.AuthController;
import n.samsonov.newsfeed.dto.AuthUserDto;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.LoginUserDto;
import n.samsonov.newsfeed.dto.RegisterUserDto;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.errors.CustomException;
import n.samsonov.newsfeed.mapper.UserMapper;
import n.samsonov.newsfeed.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

//    @BeforeEach
//    public void forAuth() {
//
//        var userForAuth = new UserEntity()
//                .setAvatar("pumpedup")
//                .setEmail("mmmtestss@sadness.com")
//                .setName("pipka")
//                .setRole("user")
//                .setPassword("654321");
//
//        userRepository.save(userForAuth);
//    }

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
    }


    @Test
    public void createAndSaveUserTest() throws Exception {

        var user = new RegisterUserDto()
                .setAvatar("imposter")
                .setEmail("fucktests@sdness.com")
                .setName("tester")
                .setRole("user")
                .setPassword("123456");


        ResultActions response = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email",
                        is(user.getEmail())))
                .andExpect(jsonPath("$.data.role",
                        is(user.getRole())))
                .andExpect(jsonPath("$.data.name",
                        is((user.getName()))))
                .andExpect(jsonPath("$.data.token", notNullValue()));

    }

    @Test
    public void loginTest() throws Exception {

        var userForAuth = new UserEntity()
                .setAvatar("pumpedup")
                .setEmail("mmmtestss@sadness.com")
                .setName("pipka")
                .setRole("user")
                .setPassword("654321");
        userRepository.save(userForAuth);

        var authUserDto = new AuthUserDto()
                .setEmail("mmmtestss@sadness.com")
                .setPassword("654321");

        ResultActions response = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authUserDto)));

        response.andExpect(status().isOk());

    }

}