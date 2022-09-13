package com.example.demo.controller;


import com.example.demo.messages.CompanyRequest;
import com.example.demo.model.Company;
import com.example.demo.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    ICompanyService companyService;

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody CompanyRequest request){
        companyService.addCompany(request);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCompanies(){
        List<Company> company = companyService.getCompany();
        return new ResponseEntity<>(company , HttpStatus.OK);
    }
}
