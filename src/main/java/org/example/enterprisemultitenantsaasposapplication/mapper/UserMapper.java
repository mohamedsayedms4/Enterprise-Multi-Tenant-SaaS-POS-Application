package org.example.enterprisemultitenantsaasposapplication.mapper;

import org.example.enterprisemultitenantsaasposapplication.model.User;
import org.example.enterprisemultitenantsaasposapplication.payload.response.UserDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDtoResponse toDto(User user);

}
