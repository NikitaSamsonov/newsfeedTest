package n.samsonov.newsfeed.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LogsEntity.class)
public abstract class LogsEntity_ {

	public static volatile SingularAttribute<LogsEntity, LocalDateTime> createdAt;
	public static volatile SingularAttribute<LogsEntity, String> method;
	public static volatile SingularAttribute<LogsEntity, Long> id;
	public static volatile SingularAttribute<LogsEntity, Integer> statusCode;

	public static final String CREATED_AT = "createdAt";
	public static final String METHOD = "method";
	public static final String ID = "id";
	public static final String STATUS_CODE = "statusCode";

}

