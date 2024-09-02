package com.productservice.userservice.controllers;

import com.productservice.userservice.dtos.SetUserRolesRequestDto;
import com.productservice.userservice.dtos.UserDto;
import com.productservice.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetail(@PathVariable("id") Long userId){
        UserDto userDto = userService.getUserDetails(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/role")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto request){
        UserDto userDto = userService.setUserRoles(userId,request.getRoleIds());
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
