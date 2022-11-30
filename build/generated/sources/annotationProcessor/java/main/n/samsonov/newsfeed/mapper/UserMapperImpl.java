package n.samsonov.newsfeed.mapper;

import javax.annotation.processing.Generated;
import n.samsonov.newsfeed.dto.LoginUserDto;
import n.samsonov.newsfeed.dto.PutUserDto;
import n.samsonov.newsfeed.dto.PutUserDtoResponse;
import n.samsonov.newsfeed.dto.RegisterUserDto;
import n.samsonov.newsfeed.entity.UserEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-30T08:56:30+0300",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity registerUserDtoToUserEntity(RegisterUserDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.avatar( dto.getAvatar() );
        userEntity.email( dto.getEmail() );
        userEntity.name( dto.getName() );

        userEntity.role( "user" );

        return userEntity.build();
    }

    @Override
    public LoginUserDto userEntityToLoginUserDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        LoginUserDto.LoginUserDtoBuilder loginUserDto = LoginUserDto.builder();

        loginUserDto.id( entity.getId() );
        loginUserDto.avatar( entity.getAvatar() );
        loginUserDto.email( entity.getEmail() );
        loginUserDto.name( entity.getName() );

        loginUserDto.role( "user" );

        return loginUserDto.build();
    }

    @Override
    public PutUserDtoResponse putUserDtoToPutUserDtoResponse(PutUserDto dto) {
        if ( dto == null ) {
            return null;
        }

        PutUserDtoResponse putUserDtoResponse = new PutUserDtoResponse();

        putUserDtoResponse.setAvatar( dto.getAvatar() );
        putUserDtoResponse.setEmail( dto.getEmail() );
        putUserDtoResponse.setName( dto.getName() );

        putUserDtoResponse.setRole( "user" );

        return putUserDtoResponse;
    }

    @Override
    public UserEntity putUserDtoToEntity(PutUserDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.avatar( dto.getAvatar() );
        userEntity.email( dto.getEmail() );
        userEntity.name( dto.getName() );

        userEntity.role( "user" );

        return userEntity.build();
    }
}
