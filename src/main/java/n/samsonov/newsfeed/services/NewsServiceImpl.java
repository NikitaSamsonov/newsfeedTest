package n.samsonov.newsfeed.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;
import n.samsonov.newsfeed.dto.BaseSuccessResponse;
import n.samsonov.newsfeed.dto.CreateNewsSuccessResponse;
import n.samsonov.newsfeed.dto.CustomSuccessResponse;
import n.samsonov.newsfeed.dto.GetNewsOutDto;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.dto.PageableResponse;
import n.samsonov.newsfeed.entity.NewsEntity;
import n.samsonov.newsfeed.entity.TagsEntity;
import n.samsonov.newsfeed.entity.UserEntity;
import n.samsonov.newsfeed.errors.CustomException;
import n.samsonov.newsfeed.errors.ErrorEnum;
import n.samsonov.newsfeed.mapper.NewsMapper;
import n.samsonov.newsfeed.repository.NewsRepository;
import n.samsonov.newsfeed.repository.TagsRepository;
import n.samsonov.newsfeed.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static n.samsonov.newsfeed.services.FileServiceImpl.loadFile;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final TagsRepository tagsRepository;
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public CreateNewsSuccessResponse createNews(NewsDto dto, UUID id) {

        NewsEntity newsEntity = NewsMapper.INSTANCE.newsDtoToNewsEntity(dto);
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorEnum.USER_NOT_FOUND));
        newsEntity.setUser(userEntity);
        newsEntity.setUsername(userEntity.getName());
        newsEntity.setImage("" + loadFile);
        newsRepository.save(newsEntity);
        List<String> tags = dto.getTags();
        List<TagsEntity> tagsEntities = tags.stream()
                .map(title -> {
                    TagsEntity tagsEntity = new TagsEntity();
                    tagsEntity.setTitle(title);
                    tagsEntity.setNewsEntity(newsEntity);
                    return tagsEntity;
                })
                .collect(Collectors.toList());

        tagsRepository.saveAll(tagsEntities);
        newsRepository.save(newsEntity);
        return CreateNewsSuccessResponse.okWithId(newsEntity.getId());
    }

    @Override
    public CustomSuccessResponse getPaginatedNews(Integer page, Integer perPage) {
        PageRequest pageb = PageRequest.of(page - 1, perPage);
        Page<NewsEntity> entities;
        entities = newsRepository.findAll(pageb);
        List<GetNewsOutDto> content = entities.stream()
                .map(e -> NewsMapper.INSTANCE.entityToGetNewsOutDto(e)
                        .setTags(e.getTags())
                        .setUserId((e.getUser().getId()))
                        .setUsername(e.getUsername()))
                .toList();
        return CustomSuccessResponse.okWithData(
                new PageableResponse()
                        .setContent(content)
                        .setNumberOfElements(entities.getNumberOfElements()));
    }

    @Transactional
    @Override
    public PageableResponse findVariaNews(String name, String keywords, String tags, Integer page, Integer perPage) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<NewsEntity> criteriaQuery = cb.createQuery(NewsEntity.class);
        Root<NewsEntity> newsEntityRoot = criteriaQuery.from(NewsEntity.class);
        List<Predicate> predicateList = new ArrayList<>();
        List<Predicate> tagsPredicateList = new ArrayList<>();

        if (name != null) {
            predicateList.add(cb.equal(newsEntityRoot.get("userName"), name));
        }
        if (tags != null) {
            for (String tag : tags.split(",")) {
                var tagEntity = tagsRepository.findByTitle(tag);
                for (String tagCheck : tagEntity) {
                    tagsPredicateList.add(cb.isMember(tagCheck, newsEntityRoot.get("tags")));
                }
            }
            Predicate[] tagsPredicate = new Predicate[tagsPredicateList.size()];
            tagsPredicateList.toArray(tagsPredicate);
            predicateList.add(cb.or(tagsPredicate));
        }
        if (keywords != null) {
            predicateList.add(cb.like(newsEntityRoot.get("title"), keywords));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        criteriaQuery.select(newsEntityRoot).where(cb.and(predicates));
        TypedQuery<NewsEntity> query = em.createQuery(criteriaQuery);

        query.setFirstResult((page - 1) * perPage);
        query.setMaxResults(perPage);

        List<NewsEntity> newsEntityList = query.getResultList();
        return new PageableResponse<>()
                .setContent(newsEntityList.stream()
                        .map(newsEntity -> NewsMapper.INSTANCE.entityToGetNewsOutDto(newsEntity)
                                .setTags(newsEntity.getTags())
                                .setUserId(newsEntity.getUser().getId())
                                .setUsername(newsEntity.getUsername()))
                        .toList())
                .setNumberOfElements(newsEntityList.size());
    }

    @Override
    public CustomSuccessResponse getUserNews(Integer page, Integer perPage, UUID userId) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<NewsEntity> newsEntity = newsRepository.findFirstById(userId, pageable);
        return CustomSuccessResponse.okWithData(
                new PageableResponse()
                        .setContent(newsEntity.stream()
                                .map(e -> NewsMapper.INSTANCE.entityToGetNewsOutDto(e)
                                        .setTags(e.getTags())
                                        .setUserId(e.getUser().getId())
                                        .setUsername(e.getUsername()))
                                .toList())
                       .setNumberOfElements(newsEntity.getNumberOfElements()));
    }

    @Transactional
    @Override
    public BaseSuccessResponse deleteNewsById(Long id) {
        if (!newsRepository.existsById(id)) {
           throw new CustomException(ErrorEnum.NEWS_NOT_FOUND);
        }
        newsRepository.deleteById(id);
        return BaseSuccessResponse.common();
    }

    @Transactional
    @Override
    public BaseSuccessResponse changeNewsById(Long id, NewsDto dto) {
        NewsEntity entity = newsRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorEnum.NEWS_NOT_FOUND));
        newsRepository.replaceNews(id, dto.getDescription(), "" + loadFile, dto.getTitle());
        tagsRepository.deleteByNewsId(id);
        List<String> tags = dto.getTags();
        for (String tag : tags) {
            TagsEntity tagsEntity = new TagsEntity();
            tagsEntity.setNewsEntity(entity);
            tagsRepository.save(tagsEntity);
        }
        return BaseSuccessResponse.common();
    }
}
