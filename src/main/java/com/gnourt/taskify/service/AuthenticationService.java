package com.gnourt.taskify.service;

import com.gnourt.taskify.common.AppResponse;
import com.gnourt.taskify.dto.UserLoginRequest;
import com.gnourt.taskify.dto.UserRegisterRequest;
import com.gnourt.taskify.model.User;
import com.gnourt.taskify.repository.UserRepository;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

//    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
//                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
//        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AppResponse register(UserRegisterRequest userDto) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(userDto.getUsername()).isPresent()) {
            return new AppResponse(HttpStatus.CONFLICT.value(), "User already exist", null);
        }

        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

//        saveUserToken(jwt, user);

        return new AppResponse(HttpStatus.OK.value(), "User registration was successful",jwt);

    }

    public AppResponse authenticate(UserLoginRequest userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );

        User user = repository.findByUsername(userDto.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

//        revokeAllTokenByUser(user);
//        saveUserToken(jwt, user);
        return new AppResponse(HttpStatus.OK.value(), "User login was successful",jwt);


    }
//    private void revokeAllTokenByUser(User user) {
//        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
//        if(validTokens.isEmpty()) {
//            return;
//        }
//
//        validTokens.forEach(t-> {
//            t.setLoggedOut(true);
//        });
//
//        tokenRepository.saveAll(validTokens);
//    }
//    private void saveUserToken(String jwt, User user) {
//        Token token = new Token();
//        token.setToken(jwt);
//        token.setLoggedOut(false);
//        token.setUser(user);
//        tokenRepository.save(token);
//    }


}
