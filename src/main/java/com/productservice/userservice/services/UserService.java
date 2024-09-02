package com.productservice.userservice.services;

import com.productservice.userservice.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public UserDto getUserDetails(Long userId){
        return null;
    }

    public UserDto setUserRoles(Long userId, List<Long> roleIds){
        return null;
    }


}
