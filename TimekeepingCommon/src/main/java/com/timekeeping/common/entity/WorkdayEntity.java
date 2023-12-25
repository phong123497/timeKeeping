package com.timekeeping.common.entity;

import com.timekeeping.common.component.BasePersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author minhtq2 on 02/11/2023
 * @project TimeKeeping
 */
@Entity
@Table(name = "WORKDAYS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkdayEntity extends BasePersonEntity {
    @Id
    @Column(name = "WORKDAY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workdayId;

    @Column(name = "WORK_PLACE_ID")
    private Integer workPlaceId;
    @NotNull
    @Column(name = "WORKDAY")
    private Timestamp workday;
    @NotNull
    @Column(name = "OPENING_TIME")
    private Timestamp openingTime;

    @Column(name = "CLOSING_TIME")
    private Timestamp closingTime;

    @Column(name = "REST_START_TIME")
    private Timestamp restStartTime;

    @Column(name = "REST_END_TIME")
    private Timestamp restEndTime;

    @Column(name = "START_BEFORE")
    private Integer startBefore;

    @Column(name = "HEADCOUNT")
    private Integer headCount;
}
