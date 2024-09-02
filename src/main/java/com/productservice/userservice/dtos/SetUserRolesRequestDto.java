package com.productservice.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SetUserRolesRequestDto {
    private List<Long> roleIds;
}
