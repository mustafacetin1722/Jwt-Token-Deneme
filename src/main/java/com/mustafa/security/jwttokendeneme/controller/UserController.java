package com.mustafa.security.jwttokendeneme.controller;

import com.mustafa.security.jwttokendeneme.dto.AuthRequest;
import com.mustafa.security.jwttokendeneme.dto.CreateUserRequest;
import com.mustafa.security.jwttokendeneme.model.User;
import com.mustafa.security.jwttokendeneme.service.JwtService;
import com.mustafa.security.jwttokendeneme.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public UserController(UserService userService,
                          JwtService jwtService,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Hello World! this is Mustafa ";
    }

    @PostMapping("/addNewUser")
    public String addUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()){
            return jwtService.createJwt(request.username());
        }
        log.info("Invalid username " + request.username());
        throw new UsernameNotFoundException("Invalid Username " + request.username());
    }

    @GetMapping("/user")
    public String getUserString(){
        return "This is User";
    }

    @GetMapping("/admin")
    public String getAdminString(){
        return "This is Admin";
    }
}
