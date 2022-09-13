package com.example.demo.serviceimpl;

import com.example.demo.messages.CompanyRequest;
import com.example.demo.model.Company;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyServiceImpl implements ICompanyService {


    @Autowired
    CompanyRepository companyRepository;

    @Override
    public void addCompany(CompanyRequest request) {
        Company company = new Company();
        company.setId(request.getCompanyCode());
        company.setName(request.getName());
        companyRepository.save(company);
    }

    @Override
    public List<Company> getCompany() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }
}
