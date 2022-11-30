package n.samsonov.newsfeed.mapper;

import javax.annotation.processing.Generated;
import n.samsonov.newsfeed.dto.GetNewsOutDto;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.entity.NewsEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-30T08:56:30+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (JetBrains s.r.o.)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsEntity newsDtoToNewsEntity(NewsDto dto) {
        if ( dto == null ) {
            return null;
        }

        NewsEntity newsEntity = new NewsEntity();

        newsEntity.setDescription( dto.getDescription() );
        newsEntity.setImage( dto.getImage() );
        newsEntity.setTitle( dto.getTitle() );

        return newsEntity;
    }

    @Override
    public GetNewsOutDto entityToGetNewsOutDto(NewsEntity entity) {
        if ( entity == null ) {
            return null;
        }

        GetNewsOutDto getNewsOutDto = new GetNewsOutDto();

        getNewsOutDto.setImage( entity.getImage() );
        getNewsOutDto.setDescription( entity.getDescription() );
        getNewsOutDto.setTitle( entity.getTitle() );
        getNewsOutDto.setId( entity.getId() );
        getNewsOutDto.setUsername( entity.getUsername() );

        return getNewsOutDto;
    }
}
