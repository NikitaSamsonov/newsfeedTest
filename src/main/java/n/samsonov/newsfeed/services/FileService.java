package n.samsonov.newsfeed.services;

import java.io.IOException;
import java.net.MalformedURLException;

import n.samsonov.newsfeed.dto.CustomSuccessResponse;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    CustomSuccessResponse save(MultipartFile file) throws IOException;

    UrlResource downloadFile(String filename) throws MalformedURLException;

}
