package n.samsonov.newsfeed.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.mapper.UserMapper;
import n.samsonov.newsfeed.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserUntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

//    @Before
//    public void setUp()  {
//        final MockHttpServletRequestBuilder defaultRequestBuilder = get("/dummy-path");
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
//                .defaultRequest(defaultRequestBuilder)
//                .alwaysDo(result -> setSessionBackOnRequestBuilder(defaultRequestBuilder, result.getRequest()))
//                .apply(springSecurity(springSecurityFilterChain))
//                .build();
//    }
//
//    private MockHttpServletRequest setSessionBackOnRequestBuilder(final MockHttpServletRequestBuilder requestBuilder,
//                                                                  final MockHttpServletRequest request) {
//        requestBuilder.session((MockHttpSession) request.getSession());
//        return request;
//    }

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

    }

    @Test
    @WithUserDetails
    public void getAllusersTest() throws Exception {

        var user1 = new UserEntity()
                .setAvatar("imposter")
                .setEmail("fucktests@sdness.com")
                .setName("tester")
                .setRole("user")
                .setPassword("123456");
        userRepository.save(user1);

        var user2 = new UserEntity()
                .setAvatar("aboba")
                .setEmail("ggwpg@sdness.com")
                .setName("nikita")
                .setRole("user")
                .setPassword("123456");
        userRepository.save(user2);

        ResultActions response = mockMvc.perform(get("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.size()",
                        is(2)));


    }

    @Test
    public void getUserInfoTest() throws Exception {

        var user1 = new UserEntity()
                .setAvatar("imposter")
                .setEmail("fucktests@sdness.com")
                .setName("tester")
                .setRole("user")
                .setPassword("123456");
        userRepository.save(user1);

        ResultActions response = mockMvc.perform(get("/api/v1/user/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1.getId())));


        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email",
                        is(user1.getEmail())))
                .andExpect(jsonPath("$.data.role",
                        is(user1.getRole())))
                .andExpect(jsonPath("$.data.id",
                        is((user1.getId()))))
                .andExpect(jsonPath("$.data.name",
                        is((user1.getName()))));

    }

    @Test
    public void getUserInfoByIdTest() throws Exception {

        var user1 = new UserEntity()
                .setAvatar("imposter")
                .setEmail("fucktests@sdness.com")
                .setName("tester")
                .setRole("user")
                .setPassword("123456");
        userRepository.save(user1);


        ResultActions response = mockMvc.perform(get("/api/v1/user/{id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1.getId())));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email",
                        is(user1.getEmail())))
                .andExpect(jsonPath("$.data.role",
                        is(user1.getRole())))
                .andExpect(jsonPath("$.data.id",
                        is((user1.getId()))))
                .andExpect(jsonPath("$.data.token", notNullValue()));

    }
}