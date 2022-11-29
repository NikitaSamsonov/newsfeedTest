package n.samsonov.newsfeed.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.services.FileService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService service;

    @PostMapping("/uploadFile")
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity(service.save(file), HttpStatus.OK);
    }

    @GetMapping("/{filename}")
    public  ResponseEntity  getFile(@PathVariable String filename) throws MalformedURLException {
        Resource file = service.downloadFile(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }
}

