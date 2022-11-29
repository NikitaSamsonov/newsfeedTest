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

    //@BeforeEach
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

        userRepository.findById(userEntity.getId());
        NewsEntity newsEntity = NewsMapper.INSTANCE.newsDtoToNewsEntity(newsDto);
        NewsEntity newsEntity1 = newsEntity.setUser(userEntity);
        newsEntity.setUsername(userEntity.getName());
        newsRepository.save(newsEntity);

        var expectSave = newsRepository.existsById(newsEntity.getId());

        Assertions.assertTrue(expectSave);

        List<String> tags = newsDto.getTags();
        List<TagsEntity> tagsEntities = tags.stream()
                .map(title -> {
                    TagsEntity tagsEntity = new TagsEntity();
                    tagsEntity.setTitle(title);
                    tagsEntity.setNewsEntity(newsEntity);

                    assertThat(tagsEntity.getNewsEntity()).isNotNull();
                    assertThat(tagsEntity.getTitle()).isNotNull();

                    return tagsEntity;
                })
                .collect(Collectors.toList());


        tagsRepository.saveAll(tagsEntities);
        newsRepository.save(newsEntity);


        var expectResponse = CreateNewsSuccessResponse.okWithId(newsEntity.getId());
        AssertionsForClassTypes.assertThat(expectResponse).isNotNull();
        AssertionsForClassTypes.assertThat(expectResponse.getId()).isNotNull();
        AssertionsForClassTypes.assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);


        cleanBase();
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

        PageRequest pageb = PageRequest.of( 0, 3);

        var entities = newsRepository.findAll(pageb);

        assertThat(entities).isNotNull();

        List<GetNewsOutDto> content = entities.stream()
                .map(e -> NewsMapper.INSTANCE.entityToGetNewsOutDto(e)
                        .setTags(e.getTags())
                        .setUserId((e.getUser().getId()))
                        .setUsername(e.getUsername()))
                .toList();


        var expectResponse = CustomSuccessResponse.okWithData(
                new PageableResponse<>()
                        .setContent(content)
                        .setNumberOfElements(entities.getNumberOfElements()));


        AssertionsForClassTypes.assertThat(expectResponse).isNotNull();
        AssertionsForClassTypes.assertThat(expectResponse.getData()).isNotNull();
        AssertionsForClassTypes.assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);

        cleanBase();
    }

    @Test
    public void getUserNewsTest() {

        var page = 1;
        var perPage = 3;

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

        Pageable pageable = PageRequest.of(page - 1, perPage);

        Page<NewsEntity> newsEntityUser = newsRepository.findFirstById(user.getId(), pageable);
        var expectResponse= CustomSuccessResponse.okWithData(
                new PageableResponse()
                        .setContent(newsEntityUser.stream()
                                .map(e -> NewsMapper.INSTANCE.entityToGetNewsOutDto(e)
                                        .setTags(e.getTags())
                                        .setUserId(e.getUser().getId())
                                        .setUsername(e.getUsername()))
                                .toList())
                        .setNumberOfElements(newsEntityUser.getNumberOfElements()));

        AssertionsForClassTypes.assertThat(expectResponse).isNotNull();
        AssertionsForClassTypes.assertThat(expectResponse.getData()).isNotNull();
        Assertions.assertEquals(2, expectResponse.getData().getNumberOfElements());
        AssertionsForClassTypes.assertThat(expectResponse)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);

        cleanBase();
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

        Assertions.assertThrows(CustomException.class,
                () -> {
                    var newsExp = newsRepository.existsById(2L);
                    if (!newsRepository.existsById(2L)) {
                        throw new CustomException(ErrorEnum.NEWS_NOT_FOUND);
                    }
                });
        Assertions.assertEquals(5, ErrorEnum.USER_NOT_FOUND.getCode());

        newsRepository.deleteById(newsEntity.getId());

        var expectResponse = BaseSuccessResponse.common();
        AssertionsForClassTypes.assertThat(expectResponse.getStatusCode()).isNotNull();
        AssertionsForClassTypes.assertThat(expectResponse)
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

        NewsEntity entity = newsRepository.findById(newsEntity.getId()).get();
        newsRepository.replaceNews(newsEntity.getId(),
                newsDto.getDescription(),
                "" + loadFile,
                newsDto.getTitle());
        tagsRepository.deleteByNewsId(newsEntity.getId());
        List<String> tags = newsDto.getTags();
        for (String tag : tags) {
            TagsEntity tagsEntity = new TagsEntity();
            tagsEntity.setNewsEntity(entity);
            assertThat(tagsEntity).isNotNull();
            tagsRepository.save(tagsEntity);

        }

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


        //cleanBase();
    }

}

