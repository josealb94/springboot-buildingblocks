package com.josegallegos.restservices.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.josegallegos.restservices.dtos.UserMsDto;
import com.josegallegos.restservices.entities.User;

@Mapper(componentModel = "Spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	//User to UserMsDTO
	@Mappings({
		@Mapping(source = "email", target = "emailaddress"),
		@Mapping(source = "username", target = "rolename")
	})
	
	UserMsDto userToUserDto(User user);
	
	//List<User> to List<UserMsDto>
	List<UserMsDto> usersToUserDtos(List<User> users);
}
