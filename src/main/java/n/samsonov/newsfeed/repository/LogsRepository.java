package n.samsonov.newsfeed.repository;

import n.samsonov.newsfeed.entity.LogsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<LogsEntity, Long> {

}
