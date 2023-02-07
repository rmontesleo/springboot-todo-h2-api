package com.demo.todoapi.controllers;

import com.demo.todoapi.dto.UserRequest;
import com.demo.todoapi.entities.User;
import com.demo.todoapi.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User> > fetchAllUsers(){
        log.debug("Get all users");
        return ResponseEntity.ok( userService.findAllUsers() );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> fetchUser(@PathVariable final long userId ){
        return  userService.findUserById( userId )
                .map(ResponseEntity::ok)
                .orElse( ResponseEntity.notFound().build() );
    }


    @PostMapping("/signup")
    public ResponseEntity<User> signup( @RequestBody @Valid final UserRequest user){
        if ( userService.findUser(user.userName()) ){
            return  ResponseEntity.status( HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body( userService.createNewUser(user) );
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login( @RequestBody @Valid final UserRequest user ){
        log.debug("## User to save is {}", user );
        return userService.findByCredentials( user)
                .map(ResponseEntity::ok)
                .orElse( ResponseEntity.notFound().build() );
    }

}
