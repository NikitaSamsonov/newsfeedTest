package n.samsonov.newsfeed.entity;

import java.util.UUID;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public abstract class UserEntity_ {

	public static volatile SingularAttribute<UserEntity, String> password;
	public static volatile SingularAttribute<UserEntity, String> role;
	public static volatile SingularAttribute<UserEntity, String> name;
	public static volatile SingularAttribute<UserEntity, UUID> id;
	public static volatile SingularAttribute<UserEntity, String> avatar;
	public static volatile ListAttribute<UserEntity, NewsEntity> newsEntities;
	public static volatile SingularAttribute<UserEntity, String> email;

	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String AVATAR = "avatar";
	public static final String NEWS_ENTITIES = "newsEntities";
	public static final String EMAIL = "email";

}

