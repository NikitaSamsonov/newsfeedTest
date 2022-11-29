package n.samsonov.newsfeed.services;

import java.util.UUID;

import n.samsonov.newsfeed.dto.BaseSuccessResponse;
import n.samsonov.newsfeed.dto.CreateNewsSuccessResponse;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.dto.PageableResponse;

public interface NewsService {
    CreateNewsSuccessResponse createNews(NewsDto dto, UUID id);

    CustomSuccessResponse getPaginatedNews(Integer page, Integer perPage);

    PageableResponse findVariaNews(String name, String keywords, String tags, Integer page, Integer perPage);

    CustomSuccessResponse getUserNews(Integer page, Integer perPage, UUID userId);

    BaseSuccessResponse deleteNewsById(Long id);

    BaseSuccessResponse changeNewsById(Long id, NewsDto dto);
}
