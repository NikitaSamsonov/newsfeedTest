package n.samsonov.newsfeed.repository;

import java.util.UUID;

import n.samsonov.newsfeed.entity.NewsEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Query("select n from NewsEntity n where n.user.id =:id")
    Page<NewsEntity> findFirstById(UUID id, Pageable pageable);

    @Modifying
    void deleteById(Long id);

    @Modifying
    @Query("UPDATE NewsEntity n SET n.description =:description, n.image =:image, n.title =:title  WHERE n.id =:id")
    void replaceNews(Long id, String description, String image, String title);

    @Query("SElECT n FROM NewsEntity n WHERE n.id =:id")
    NewsEntity getById(Long id);
}
