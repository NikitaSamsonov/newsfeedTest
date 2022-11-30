package n.samsonov.newsfeed.serviceTest;

import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.repository.NewsRepository;
import n.samsonov.newsfeed.repository.UserRepository;
import n.samsonov.newsfeed.services.AuthService;
import n.samsonov.newsfeed.services.FileService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanBase(){
        userRepository.deleteAll();
    }

    @Test
    public void saveFileTest() throws IOException {

        String text ="Text to be uploaded.";
        MockMultipartFile file = new MockMultipartFile("file","test.txt", "text/plain", text.getBytes());

        var expectOutput = fileService.save(file);

        AssertionsForClassTypes.assertThat(expectOutput).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput.getData()).isNotNull();
        AssertionsForClassTypes.assertThat(expectOutput)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("statusCode", 1);


    }

    @Test
    public void downloadFileTest() throws IOException {

        var expectOutput = fileService.downloadFile("5a472c29-89ff-4a19-9983-d2ef8bc471cb.txt");

        assertThat(expectOutput).isNotNull();
    }
}
