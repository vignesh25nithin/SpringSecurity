package com.example.demo.serviceimpl;

import com.example.demo.messages.UserDTO;
import com.example.demo.model.Company;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IUserCompanyMapService;
import com.example.demo.service.IUserRoleMapService;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IUserRoleMapService iUserRoleMapService;

    @Autowired
    IUserCompanyMapService iUserCompanyMapService;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> user = userRepository.findAll();
        return user;
    }

    @Override
    public UserDTO getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User userVal = user.get();
            List<Company> companies = iUserCompanyMapService.getUserCompanyMappings(userVal.getId());
            List<Role> role = iUserRoleMapService.getUserRoleMappings(userVal.getId());

            UserDTO dto = new UserDTO(role , companies , userVal.getName() , userVal.getId());
            return dto;
        }
        return null;
    }

    @Override
    public UserDTO getUserByName(String id) {

        if(userRepository.existsById(id)){
            User user = userRepository.findById(id).get();
            List<Company> companies = iUserCompanyMapService.getUserCompanyMappings(user.getId());
            List<Role> role = iUserRoleMapService.getUserRoleMappings(user.getId());

            UserDTO dto = new UserDTO(role , companies , user.getName() , user.getId());
            return dto;
        }
        return null;
    }

    @Override
    public void update(String name ,String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
          User userVal = user.get();
          userVal.setName(name);
          userRepository.save(userVal);
        }
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }
}
