package com.example.demo.messages;

import com.example.demo.model.Company;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    List<Role> roles;
    List<Company> company;
    String name;
    String id;

    public UserDTO(List<Role> roles, List<Company> company, String name, String id) {
        this.roles = roles;
        this.company = company;
        this.name = name;
        this.id = id;
    }
}
