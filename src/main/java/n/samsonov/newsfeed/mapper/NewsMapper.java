package n.samsonov.newsfeed.mapper;

import n.samsonov.newsfeed.dto.GetNewsOutDto;
import n.samsonov.newsfeed.dto.NewsDto;
import n.samsonov.newsfeed.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);
    @Mappings({
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "image", target = "image"),
            @Mapping(target = "tags", ignore = true),
            @Mapping(source = "title", target = "title")
    })
    NewsEntity newsDtoToNewsEntity(NewsDto dto);

    @Mappings({
            @Mapping(source = "image", target = "image"),
            @Mapping(source = "description", target = "description"),
            @Mapping(target = "tags", ignore = true),
            @Mapping(source = "title", target = "title")
    })
    GetNewsOutDto entityToGetNewsOutDto(NewsEntity entity);

}

