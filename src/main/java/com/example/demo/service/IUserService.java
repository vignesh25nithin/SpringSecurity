package com.example.demo.service;

import com.example.demo.messages.UserDTO;
import com.example.demo.model.User;

import java.util.List;

public interface IUserService {

    void save(User user);
    List<User> getAllUser();
    UserDTO getUser(String id);
    UserDTO getUserByName(String id);
    void update(String name , String id);
    void delete(String id);
}
