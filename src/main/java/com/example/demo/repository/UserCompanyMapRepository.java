package com.example.demo.repository;

import com.example.demo.model.UserCompanyMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCompanyMapRepository extends JpaRepository<UserCompanyMapping, Integer> {

    UserCompanyMapping findByUserIdAndCompanyId(@Param("userId") String userId , @Param("companyId") String companyId);

    void deleteByUserIdAndCompanyId(@Param("userId") String userId , @Param("companyId") String companyId);

    List<UserCompanyMapping> findByUserId(@Param("userId") String userId);
}