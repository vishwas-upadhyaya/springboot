package com.productservice.userservice.controllers;

import com.productservice.userservice.dtos.CreateRoleRequestDto;
import com.productservice.userservice.models.Role;
import com.productservice.userservice.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;
    RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(CreateRoleRequestDto request){
//        Role role = roleService.createRole(request.getName());
//        return new ResponseEntity<>(role, HttpStatus.OK);
        return null;
    }


}
