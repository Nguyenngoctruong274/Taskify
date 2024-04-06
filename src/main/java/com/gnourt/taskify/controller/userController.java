package com.gnourt.taskify.controller;

import com.gnourt.taskify.common.AppResponse;
import com.gnourt.taskify.dto.UserLoginRequest;
import com.gnourt.taskify.dto.UserRegisterRequest;
import com.gnourt.taskify.repository.UserRepository;
import com.gnourt.taskify.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {
    private final AuthenticationService authenService;


    public userController(AuthenticationService authenService) {
        this.authenService = authenService;
    }
    @PostMapping("/register")
    public ResponseEntity<AppResponse> register(@RequestBody UserRegisterRequest userDto){
        return ResponseEntity.ok(authenService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse> login(@RequestBody UserLoginRequest userDto){
        return ResponseEntity.ok(authenService.authenticate(userDto));
    }

}
