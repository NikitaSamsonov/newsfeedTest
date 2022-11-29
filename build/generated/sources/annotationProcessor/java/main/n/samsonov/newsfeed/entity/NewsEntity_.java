package n.samsonov.newsfeed.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NewsEntity.class)
public abstract class NewsEntity_ {

	public static volatile SingularAttribute<NewsEntity, String> image;
	public static volatile SingularAttribute<NewsEntity, String> description;
	public static volatile SingularAttribute<NewsEntity, Long> id;
	public static volatile SingularAttribute<NewsEntity, String> title;
	public static volatile SingularAttribute<NewsEntity, UserEntity> user;
	public static volatile SingularAttribute<NewsEntity, String> username;
	public static volatile ListAttribute<NewsEntity, TagsEntity> tags;

	public static final String IMAGE = "image";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String USER = "user";
	public static final String USERNAME = "username";
	public static final String TAGS = "tags";

}

