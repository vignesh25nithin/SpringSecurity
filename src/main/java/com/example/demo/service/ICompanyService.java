package com.example.demo.service;

import com.example.demo.messages.CompanyRequest;
import com.example.demo.model.Company;

import java.util.List;

public interface ICompanyService {

    void addCompany(CompanyRequest request);

    List<Company> getCompany();
}
