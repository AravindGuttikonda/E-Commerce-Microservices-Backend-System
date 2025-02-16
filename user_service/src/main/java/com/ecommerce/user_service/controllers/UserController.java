package com.ecommerce.user_service.controllers;

import com.ecommerce.user_service.dtos.UserRegisterDto;
import com.ecommerce.user_service.exceptions.UserNotFoundException;
import com.ecommerce.user_service.models.UserData;
import com.ecommerce.user_service.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserRegisterDto userRegisterDto) throws JsonProcessingException {
        UserData userData = userService.addUser(userRegisterDto);
        return new ResponseEntity<>("User: " + userData.getName() + "with UserId: " + userData.getId() + "is registered successfully.", HttpStatus.OK);
    }
    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserData> getUser(@PathVariable("id") Long id) throws UserNotFoundException {
        UserData user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
