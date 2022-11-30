package n.samsonov.newsfeed.controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import n.samsonov.newsfeed.controllers.NewsController;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.mapper.UserMapper;
import n.samsonov.newsfeed.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanBase(){
        userRepository.deleteAll();
    }

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser(value = "ponyal")
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
