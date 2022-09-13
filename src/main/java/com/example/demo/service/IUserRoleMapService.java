package com.example.demo.service;

import com.example.demo.model.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserRoleMapService {

    ResponseEntity<?> add(String userId , String roleId);
    void delete(String userId , String roleId);
    List<Role> getUserRoleMappings(String userId);
    List<String> getRoleMappings(String userId);

    String[] getRoleMappingsArray(String userId);
}
