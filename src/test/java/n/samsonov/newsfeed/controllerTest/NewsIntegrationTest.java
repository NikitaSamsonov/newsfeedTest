package n.samsonov.newsfeed.controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import n.samsonov.newsfeed.WebSecurityConfigurer;
import n.samsonov.newsfeed.controllers.AuthController;
import n.samsonov.newsfeed.controllers.NewsController;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.mapper.UserMapper;
import n.samsonov.newsfeed.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@WithUserDetails("imposter")
public class NewsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    void cleanBase(){
//        userRepository.deleteAll();
//        var userForAuth = new UserEntity()
//                .setAvatar("pumpedup")
//                .setEmail("mmmtestss@sadness.com")
//                .setName("pipka")
//                .setRole("user")
//                .setPassword("654321");
//        userRepository.save(userForAuth);
//    }

    @Autowired
    private WebApplicationContext context;

//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }


    @Test
    public void createNewsTest() throws Exception {
        UserEntity userEntity = new UserEntity().setName("ponyal");
        userRepository.save(userEntity);

        NewsDto newsDto = new NewsDto ()
                .setDescription("string")
                .setImage("aboba")
                .setTitle("user")
                .setTags(List.of("ggvp"));

        ResultActions response = mockMvc.perform(post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newsDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.description",
                        is(newsDto.getDescription())))
                .andExpect(jsonPath("$.data.image",
                        is(newsDto.getImage())))
                .andExpect(jsonPath("$.data.title",
                        is(newsDto.getTitle())))
                .andExpect(jsonPath("$.data.id",
                        notNullValue()));
    }
}
