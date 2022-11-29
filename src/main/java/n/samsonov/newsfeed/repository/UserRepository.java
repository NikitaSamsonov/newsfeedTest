package n.samsonov.newsfeed.repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import n.samsonov.newsfeed.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u")
    List<PublicUserView> viewAllUsers();

    @Query("SELECT u FROM UserEntity u WHERE u.id =:id ")
    PublicUserView viewUserById(UUID id);

    Optional<UserEntity> findById(UUID id);

    @Modifying
    @Query("UPDATE UserEntity u SET u.email =:email, u.name =:name, u.avatar =:avatar, u.role =:role WHERE u.id =:id")
    void updateUserInfo(String email, String name, String avatar, String role, UUID id);

    UserEntity findByName(String name);

}
