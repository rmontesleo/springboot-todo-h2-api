package com.demo.todoapi.services;

import com.demo.todoapi.dto.UserRequest;
import com.demo.todoapi.entities.User;
import com.demo.todoapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public boolean findUser(final String userName){
        return userRepository.findByUserName( userName ).isPresent();
    }

    public User createNewUser( UserRequest request ){
        User user = User.builder()
                        .userName( request.userName() )
                        .password( passwordEncoder.encode( request.password() ) )
                        .build();
        return userRepository.save(user);
    }

    public Optional<Long> findByCredentials(UserRequest user){
        return userRepository.findByUserName( user.userName() )
                .map( wanted ->{
                    boolean response =  passwordEncoder.matches( user.password(), wanted.getPassword()  );
                    if( response ){
                        return  wanted.getUserId();
                    }
                    return null;
                });
    }

    public Optional<User> findUserById( final long userId){
        return userRepository.findById(userId);
    }


}
