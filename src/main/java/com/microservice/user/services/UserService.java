package com.microservice.user.services;

import com.microservice.user.entity.ResponseDto;
import com.microservice.user.entity.User;

public interface UserService {
    User saveUser(User user);
    ResponseDto getUser(Long userid);
}
