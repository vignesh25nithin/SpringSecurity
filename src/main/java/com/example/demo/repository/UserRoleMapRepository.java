package com.example.demo.repository;

import com.example.demo.model.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleMapRepository extends JpaRepository<UserRoleMapping, Integer> {

    UserRoleMapping findByUserIdAndRoleId(@Param("userId") String userId , @Param("roleId") String roleId);

    void deleteByUserIdAndRoleId(@Param("userId") String userId , @Param("roleId") String roleId);

    List<UserRoleMapping> findByUserId(@Param("userId") String userId);
}