package n.samsonov.newsfeed.mapper;

import n.samsonov.newsfeed.dto.LoginUserDto;
import n.samsonov.newsfeed.dto.PutUserDto;
import n.samsonov.newsfeed.dto.PutUserDtoResponse;
import n.samsonov.newsfeed.dto.RegisterUserDto;
import n.samsonov.newsfeed.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
        @Mappings({
                @Mapping(target = "id", ignore = true),
                @Mapping(target = "password", ignore = true),
                @Mapping(source = "dto.avatar", target = "avatar"),
                @Mapping(source = "dto.email", target = "email"),
                @Mapping(source = "dto.name", target = "name"),
                @Mapping(expression = "java(\"user\")", target = "role")
        })
        UserEntity registerUserDtoToUserEntity(RegisterUserDto dto);


    @Mappings({
            @Mapping(target = "token", ignore = true),
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "avatar", target = "avatar"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "name", target = "name"),
            @Mapping(expression = "java(\"user\")", target = "role")
    })
    LoginUserDto userEntityToLoginUserDto(UserEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "avatar", target = "avatar"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "name", target = "name"),
            @Mapping(expression = "java(\"user\")", target = "role")
    })
    PutUserDtoResponse putUserDtoToPutUserDtoResponse(PutUserDto dto);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "password", ignore = true),
        @Mapping(source = "dto.avatar", target = "avatar"),
        @Mapping(source = "dto.email", target = "email"),
        @Mapping(source = "dto.name", target = "name"),
        @Mapping(expression = "java(\"user\")", target = "role")
    })
    UserEntity putUserDtoToEntity(PutUserDto dto);
}

