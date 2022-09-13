package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    IRolesService rolesService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Role role){
        rolesService.save(role);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> get(){
        List<Role> roles = rolesService.getRoles();
        return new ResponseEntity<>(roles , HttpStatus.OK);
    }
}
