package com.timekeeping.common.entity;

import com.timekeeping.common.component.BasePersonEntity;
import lombok.*;

import javax.persistence.*;

/**
 * @author minhtq2 on 02/11/2023
 * @project TimeKeeping
 */
@Entity
@Table(name="WORKDAY_DETAILS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkdayDetailEntity extends BasePersonEntity {

    @Id
    @Column(name = "WORKDAY_DETAIL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workdayDetailId;

    @Column(name = "WORKDAY_ID")
    private Integer workdayId;

    @Column(name = "EMPLOYEE_ID")
    private String employeeId;

    @Column(name = "NOTE")
    private String note;
}
