package n.samsonov.newsfeed.repository;

import java.util.List;

import n.samsonov.newsfeed.entity.TagsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, Long> {

    @Query("SELECT u FROM TagsEntity u WHERE u.title =:title ")
    List<String> findByTitle(String title);

    @Modifying
    @Query("DELETE FROM TagsEntity t WHERE t.newsEntity.id = :id")
    void deleteByNewsId(Long id);
}

