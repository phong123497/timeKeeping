package com.timekeeping.common.entity;


import javax.persistence.*;

import com.timekeeping.common.component.BasePersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hslf.record.CString;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author minhtq2 on 10/10/2023
 * @project timekeeping-common
 */
@Entity
@Table(name="USERS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BasePersonEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "EMPLOYEE_ID")
    private String employeeId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "NAME")
    private String name;

    @Column(name = "WORK_PLACE_ID")
    private Integer workPlaceId;

    @Column(name = "AUTHENTICATION_FAILURE")
    private String authenticationFailure;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "BIRTHDAY")
    private Timestamp birthday;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "POST_CODE")
    private String postCode;

    @Column(name = "DELETED_FG")
    @Builder.Default
    private Integer deletedFg = 0;

    public UserEntity(String username) {
        this.username = username;
    }
}
