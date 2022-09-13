package com.example.demo.service;

import com.example.demo.model.Role;

import java.util.List;

public interface IRolesService {

    void save(Role role);
    List<Role> getRoles();
}
