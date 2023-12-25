package com.timekeeping.common.component;

import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.util.DateUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author minhtq2 on 02/11/2023
 * @project TimeKeeping
 */

@Data
@MappedSuperclass
public class BasePersonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "CREATED_ID")
    private String createdId;

    @Column(name = "UPDATED_ID")
    private String updatedId;

    @Column(name = "CREATED_DT")
    private Timestamp createdDt;

    @Column(name = "UPDATED_DT")
    private Timestamp updatedDt;

    public void setRegisterField(UserEntity currentUser) {
        createdId = currentUser.getEmployeeId();
        createdDt = DateUtils.getCurrentTimestamp();
        updatedId = currentUser.getEmployeeId();
        updatedDt = DateUtils.getCurrentTimestamp();
    }
}
