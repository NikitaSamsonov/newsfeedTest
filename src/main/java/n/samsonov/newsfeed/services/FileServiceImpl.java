package n.samsonov.newsfeed.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final Path directory = Paths.get("uploads").toAbsolutePath();

    @Value("${path.file}")
    private String pathFile;
    public static String loadFile;

    @Override
    public UrlResource downloadFile(String filename) throws MalformedURLException {
        Path file = directory.resolve(filename);
        return new UrlResource(file.toUri());
    }

    @Override
    public CustomSuccessResponse save(MultipartFile file) throws IOException {
        String filename = randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Files.copy(file.getInputStream(), directory.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        loadFile = pathFile + filename;
        return CustomSuccessResponse.okWithData(loadFile);
    }
}

