package com.example.demo.serviceimpl;

import com.example.demo.model.Role;
import com.example.demo.repository.RolesRepository;
import com.example.demo.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleServiceImpl implements IRolesService {

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public void save(Role role) {
        rolesRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return rolesRepository.findAll();
    }
}
