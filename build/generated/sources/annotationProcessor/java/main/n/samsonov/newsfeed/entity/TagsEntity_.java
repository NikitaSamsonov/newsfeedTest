package n.samsonov.newsfeed.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TagsEntity.class)
public abstract class TagsEntity_ {

	public static volatile SingularAttribute<TagsEntity, Long> id;
	public static volatile SingularAttribute<TagsEntity, String> title;
	public static volatile SingularAttribute<TagsEntity, NewsEntity> newsEntity;

	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String NEWS_ENTITY = "newsEntity";

}

