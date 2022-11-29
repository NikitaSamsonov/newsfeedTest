package n.samsonov.newsfeed.serviceTest;

import n.samsonov.newsfeed.dto.BaseSuccessResponse;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.entity.NewsEntity;
import n.samsonov.newsfeed.entity.TagsEntity;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.repository.NewsRepository;
import n.samsonov.newsfeed.repository.TagsRepository;
import n.samsonov.newsfeed.repository.UserRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static n.samsonov.newsfeed.services.FileServiceImpl.loadFile;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TagsRepository tagsRepository;

    private UserEntity user = new UserEntity().setName("ponyal");


    private NewsEntity newsEntity = new NewsEntity()
            .setDescription("string")
            .setImage("aboba")
            .setTitle("sdnes")
            .setUser(user);

    private NewsDto newsDto = new NewsDto()
            .setDescription("string")
            .setImage("aboba")
            .setTitle("user")
            .setTags(List.of("ggvp", "sdnes"));

    @BeforeEach
    public void prepareBase(){
        userRepository.save(user);
        newsRepository.save(newsEntity);
    }

    @Test
    @Transactional
    public void changeNewsByIdTest() {

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

   }

}