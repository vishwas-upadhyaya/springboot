package com.productservice.userservice.Security;

import com.productservice.userservice.models.User;
import com.productservice.userservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomSpringUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    CustomSpringUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("this user does not exist");
        }

        User user = optionalUser.get();

        UserDetails userDetails = new CustomUserDetail(user);
        return userDetails;
    }
}
