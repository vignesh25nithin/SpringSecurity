package com.example.demo.service;

import com.example.demo.model.Company;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserCompanyMapService {
    ResponseEntity<?> add(String userId , String companyId);
    void delete(String userId , String companyId);
    List<Company> getUserCompanyMappings(String userId);
}
