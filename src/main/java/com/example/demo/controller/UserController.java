package com.example.demo.controller;

import com.example.demo.messages.CompanyRequest;
import com.example.demo.messages.UserDTO;
import com.example.demo.model.Company;
import com.example.demo.model.User;
import com.example.demo.service.IRolesService;
import com.example.demo.service.IUserCompanyMapService;
import com.example.demo.service.IUserRoleMapService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @Autowired
    IUserRoleMapService iUserRoleMapService;

    @Autowired
    IUserCompanyMapService iUserCompanyMapService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user){
        iUserService.save(user);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable(value = "id")String id){
        UserDTO user = iUserService.getUser(id);
        return new ResponseEntity<>(user , HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        List<User> user = iUserService.getAllUser();
        return new ResponseEntity<>(user , HttpStatus.OK);
    }

    @PostMapping("/user-role")
    public ResponseEntity<?> userRoleMap(@RequestParam(value ="userId") String userId,
                                         @RequestParam(value ="roleId")  String roleId){
        iUserRoleMapService.add(userId, roleId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/user-company")
    public ResponseEntity<?> userCompanyMap(@RequestParam(value ="userId") String userId,
                                         @RequestParam(value ="companyId")  String companyId){
        iUserCompanyMapService.add(userId, companyId);
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @GetMapping("/allRoles/{id}")
    public ResponseEntity<?> getAllUserRole(@PathVariable(value = "id")String id){
        return new ResponseEntity<>( iUserRoleMapService.getUserRoleMappings(id) , HttpStatus.OK);
    }

}
