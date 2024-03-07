package com.orm.testing.mappers;

import com.orm.mappers.Mapper;
import com.orm.testing.dtos.UserDto;
import com.orm.testing.models.User;

public class UserProfile extends Mapper {
    public User map(UserDto userDto) {
        return this.createMap(userDto, User.class);
    }
}
