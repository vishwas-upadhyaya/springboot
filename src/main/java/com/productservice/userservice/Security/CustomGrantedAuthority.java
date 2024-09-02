package com.productservice.userservice.Security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.productservice.userservice.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@JsonDeserialize(as=CustomGrantedAuthority.class)
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private Role role;
    CustomGrantedAuthority(Role role){
//        if(role!=null)
            this.role = role;
//            System.out.println();
    }
    @Override
    @JsonIgnore
    public String getAuthority() {
        return role.getRole();
    }
}
