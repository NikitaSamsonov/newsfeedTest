package n.samsonov.newsfeed.repository;

import n.samsonov.newsfeed.entity.LogsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<LogsEntity, Long> {

    @Query("SELECT l  FROM LogsEntity l")
    List<LogsEntity> getAll();

}
