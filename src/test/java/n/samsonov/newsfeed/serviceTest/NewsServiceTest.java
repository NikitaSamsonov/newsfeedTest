package n.samsonov.newsfeed.serviceTest;

import n.samsonov.newsfeed.dto.*;
import n.samsonov.newsfeed.entity.NewsEntity;
import n.samsonov.newsfeed.entity.TagsEntity;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.errors.CustomException;
import n.samsonov.newsfeed.errors.ErrorEnum;
import n.samsonov.newsfeed.mapper.NewsMapper;
import n.samsonov.newsfeed.repository.NewsRepository;
import n.samsonov.newsfeed.repository.TagsRepository;
import n.samsonov.newsfeed.repository.UserRepository;
import n.samsonov.newsfeed.services.NewsService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static n.samsonov.newsfeed.services.FileServiceImpl.loadFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

@SpringBootTest
public class NewsServiceTest {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private NewsService newsService;

    @BeforeEach
    void cleanBase(){
        newsRepository.deleteAll();
    }

    public UUID commonUser() {
        var user =  new UserEntity().setName("ponyal");
        return user.getId();
    }


    @Test
    public void createNewsTest(){

        UserEntity userEntity = new UserEntity().setName("ponyal");
        userRepository.save(userEntity);

        NewsDto newsDto = new NewsDto ()
                .setDescription("string")
                .setImage("aboba")
                .setTitle("user")
                .setTags(List.of("ggvp"));

        var expectOutput = newsService.createNews(newsDto, userEntity.getId());

        assertThat(expectOutput).isNotNull();
        assertThat(expectOutput.getId()).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);


        var expectSave = newsRepository.existsById(expectOutput.getId());

        Assertions.assertTrue(expectSave);

    }

    @Test
    public void getPaginatedNewsTest(){

        var user =  new UserEntity().setName("ponyal");
        userRepository.save(user);


        var newsEntity = new NewsEntity()
                .setDescription("string")
                .setImage("aboba")
                .setTitle("sdnes")
                .setUser(user);


        var newsEntity2 = new NewsEntity ()
                .setDescription("mudrost")
                .setImage("volk.jpeg")
                .setTitle("circus")
                .setUser(user);

        newsRepository.save(newsEntity);
        newsRepository.save(newsEntity2);

        var expectOutput = newsService.getPaginatedNews(1,3);

        AssertionsForClassTypes.assertThat(expectOutput).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput.getData()).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);

    }

    @Test
    public void getUserNewsTest() {

        var user =  new UserEntity().setName("ponyal");
        userRepository.save(user);

        var newsEntity = new NewsEntity()
                .setDescription("string")
                .setImage("aboba")
                .setTitle("sdnes")
                .setUser(user);

        var newsEntity2 = new NewsEntity ()
                .setDescription("mudrost")
                .setImage("volk.jpeg")
                .setTitle("circus")
                .setUser(user);

        newsRepository.save(newsEntity);
        newsRepository.save(newsEntity2);

        var expectOutput = newsService.getUserNews(1,3, user.getId());

        AssertionsForClassTypes.assertThat(expectOutput).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput.getData()).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);

    }

    @Transactional
    @Test
    public void deleteNewsByIdTest() {

        var user =  new UserEntity().setName("ponyal");
        userRepository.save(user);

        var newsEntity = new NewsEntity()
                .setDescription("string")
                .setImage("aboba")
                .setTitle("sdnes")
                .setUser(user);
        newsRepository.save(newsEntity);

        var expectOutput = newsService.deleteNewsById(newsEntity.getId());
        AssertionsForClassTypes.assertThat(expectOutput.getStatusCode()).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("statusCode", 1)
                .hasFieldOrPropertyWithValue("success", true);
    }

    @Transactional
    @Test
    public void changeNewsByIdTest() {

        var user =  new UserEntity().setName("ponyal");
        userRepository.save(user);

        var newsEntity = new NewsEntity()
                .setDescription("string")
                .setImage("aboba")
                .setTitle("sdnes")
                .setUser(user);

       var newsDto = new NewsDto()
                .setDescription("string")
                .setImage("aboba")
                .setTitle("user")
                .setTags(List.of("ggvp", "sdnes"));

        newsRepository.save(newsEntity);

        var idNews = newsEntity.getId();

        newsService.changeNewsById(idNews,newsDto);


        var changedNews = newsRepository.getById(newsEntity.getId());

        assertThat(changedNews)
                .hasFieldOrPropertyWithValue("description", newsDto.getDescription())
                .hasFieldOrPropertyWithValue("title", newsDto.getTitle())
                .hasFieldOrPropertyWithValue("image", newsDto.getImage())
                .hasFieldOrPropertyWithValue("tags", newsDto.getTags());

        var expectResponse = BaseSuccessResponse.common();
        AssertionsForClassTypes.assertThat(expectResponse.getStatusCode()).isNotNull();
        AssertionsForClassTypes.assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("statusCode", 1)
                .hasFieldOrPropertyWithValue("success", true);

    }

}

