package com.example.demo.serviceimpl;

import com.example.demo.model.*;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleMapRepository;
import com.example.demo.service.IUserRoleMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.*;


@Component
public class UserRoleMapServiceImpl implements IUserRoleMapService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UserRoleMapRepository userRoleMapRepository;


    @Override
    public ResponseEntity<?> add(String userId, String roleId) {
        UserRoleMapping roleMapping = userRoleMapRepository.findByUserIdAndRoleId(userId , roleId);
        if(roleMapping != null){
            return new ResponseEntity<>("Mapping already exists" , HttpStatus.BAD_REQUEST);
        }

        Random rand = new Random();
        // Generate random integers in range 0 to 999
        int id = rand.nextInt(1000);
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            return new ResponseEntity<>("user not exists" , HttpStatus.BAD_REQUEST);
        }
        Optional<Role> role = rolesRepository.findById(roleId);

        if(!role.isPresent()){
            return new ResponseEntity<>("user not exists" , HttpStatus.BAD_REQUEST);
        }

        try{
            UserRoleMapping mapping = new UserRoleMapping();
            mapping.setRole(role.get());
            mapping.setUser(user.get());
            mapping.setId(id);
            userRoleMapRepository.save(mapping);
            return new ResponseEntity<>("Mapping added successfully" , HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("unable to map user and role" , HttpStatus.FORBIDDEN);
        }

    }

    @Override
    public void delete(String userId, String roleId) {
        userRoleMapRepository.deleteByUserIdAndRoleId(userId , roleId);
    }

    @Override
    public List<Role> getUserRoleMappings(String userId) {
        List<UserRoleMapping> roles = userRoleMapRepository.findByUserId(userId);
        if(roles != null){
            List<Role> roleList = new ArrayList<>();
            for(UserRoleMapping mapping : roles){
                roleList.add(mapping.getRole());
            }
            return roleList;

        }
        return Collections.EMPTY_LIST;
    }
@Override
    public List<String> getRoleMappings(String userId) {
     List<String> list=new ArrayList<>();
            List<Role> roleList = getUserRoleMappings(userId);
            for(Role mapping : roleList){
                list.add(mapping.getName());
            }
            return list;
        }
    @Override
    public String[] getRoleMappingsArray(String userId) {

        List<Role> roleList = getUserRoleMappings(userId);
        String[] list=new String[roleList.size()];
        int i=0;
        for(Role mapping : roleList){
            list[i]=mapping.getName();
        }
        return list;
    }


}
