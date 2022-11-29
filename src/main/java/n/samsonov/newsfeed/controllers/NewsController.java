package n.samsonov.newsfeed.controllers;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.security.UserDetailsImpl;
import n.samsonov.newsfeed.services.NewsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService service;

    @PostMapping
    public ResponseEntity createNews(@Validated @RequestBody NewsDto dto, Authentication authentication) {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        return new ResponseEntity(service.createNews(dto, userDetailsImpl.getId()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllnewsPaginated(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return new ResponseEntity(service.getPaginatedNews(page, perPage), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity findNews(@RequestParam(value = "author", required = false) String author,
                                   @RequestParam(value = "keywords", required = false) String keywords,
                                   @RequestParam(value = "tags", required = false) String tags,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "perPage", required = false) Integer perPage) {
        return new ResponseEntity(service.findVariaNews(author, keywords, tags, page, perPage), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUserNews(@RequestParam("page") Integer page,
                                      @RequestParam("perPage") Integer perPage,
                                      @PathVariable("userId") UUID userId) {
        return new ResponseEntity(service.getUserNews(page, perPage, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNewsId(@PathVariable Long id, Authentication authentication) {
        authentication.getAuthorities();
        return new ResponseEntity(service.deleteNewsById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity changeNewsById(@PathVariable Long id, @Validated @RequestBody NewsDto dto) {
        return new ResponseEntity(service.changeNewsById(id, dto), HttpStatus.OK);
    }
}
