package com.example.demo.serviceimpl;

import com.example.demo.model.Company;
import com.example.demo.model.User;
import com.example.demo.model.UserCompanyMapping;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.UserCompanyMapRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IUserCompanyMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserCompanyMapImpl implements IUserCompanyMapService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserCompanyMapRepository userCompanyMapRepository;

    @Override
    public ResponseEntity<?> add(String userId, String companyId) {
        UserCompanyMapping companyMapping = userCompanyMapRepository.findByUserIdAndCompanyId(userId , companyId);
        if(companyMapping != null){
            return new ResponseEntity<>("Mapping already exists" , HttpStatus.BAD_REQUEST);
        }

        Random rand = new Random();

        // Generate random integers in range 0 to 999
        int id = rand.nextInt(1000);
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            return new ResponseEntity<>("user not exists" , HttpStatus.BAD_REQUEST);
        }
        Optional<Company> company = companyRepository.findById(companyId);
        if(!company.isPresent()){
            return new ResponseEntity<>("company not exists" , HttpStatus.BAD_REQUEST);
        }

        try{
            UserCompanyMapping mapping = new UserCompanyMapping();
            mapping.setCompany(company.get());
            mapping.setUser(user.get());
            mapping.setId(id);
            userCompanyMapRepository.save(mapping);
            return new ResponseEntity<>("Mapping added successfully" , HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("unable to map user and company" , HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void delete(String userId, String companyId) {
        userCompanyMapRepository.deleteByUserIdAndCompanyId(userId, companyId);
    }

    @Override
    public List<Company> getUserCompanyMappings(String userId) {
        List<UserCompanyMapping> userCompanyMappings = userCompanyMapRepository.findByUserId(userId);
        if(userCompanyMappings!= null){
            List<Company> companies = new ArrayList<>();
            for(UserCompanyMapping eachMap : userCompanyMappings){
                companies.add(eachMap.getCompany());
            }
            return companies;
        }
        return Collections.emptyList();
    }
}
