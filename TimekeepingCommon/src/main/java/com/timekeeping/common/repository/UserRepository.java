package com.timekeeping.common.repository;
import com.azure.core.http.rest.Page;
import com.timekeeping.common.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
/**
 * @author minhtq2 on 11/10/2023p
 * @project timekeeping-common
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getUserEntityByEmployeeId(String employeeId);

    UserEntity getUserEntityByUsername(String username);



    /**
     * get count user
     * @return Integer
     */
    @Query(value = "select count(*) from UserEntity  as u where u.deletedFg = 0")
    Integer getCountUser();
    @Query(value = "SELECT * FROM USERS as us "
            ,countQuery = "SELECT COUNT(*) FROM USERS as us "
            ,nativeQuery = true)
    Page<UserEntity> findAll(Pageable pageable);


}
