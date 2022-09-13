package com.example.demo.controller;

import com.example.demo.configuration.JwtUtils;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.repository.UserRoleMapRepository;
import com.example.demo.service.IUserRoleMapService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login")
@Slf4j
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtTokenUtil;
    @Autowired
    private  UserRoleMapRepository userRoleMapRepository;
    @Autowired
    IUserRoleMapService iUserRoleMapService;

    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws BadCredentialsException, ExpiredJwtException {
        String[] roleList = iUserRoleMapService.getRoleMappingsArray(authenticationRequest.getUsername());
        final UserDetails userDetails = User.withDefaultPasswordEncoder().username(authenticationRequest.getUsername())
                .password(authenticationRequest.getPassword()).roles(roleList).authorities(roleList)
                .build();
        return new AuthenticationResponse(jwtTokenUtil.generateToken(userDetails));
    }
}
