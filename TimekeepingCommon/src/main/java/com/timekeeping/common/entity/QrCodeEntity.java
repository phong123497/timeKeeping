package com.timekeeping.common.entity;

import com.timekeeping.common.component.BasePersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author minhtq2 on 02/11/2023
 * @project TimeKeeping
 */
@Entity
@Table(name="QR_CODES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeEntity extends BasePersonEntity {

    @Id
    @Column(name = "QR_CODE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qrCodeId;

    @Column(name = "QR_CODE")
    private String qrCode;

    @Column(name = "EMPLOYEE_ID")
    private String employeeId;

    @Column(name = "DELETED_FG")
    @Builder.Default
    private Integer deletedFg = 0;
}
