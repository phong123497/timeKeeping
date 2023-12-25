package com.timekeeping.common.entity;

import com.timekeeping.common.component.BasePersonEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author minhtq2 on 02/11/2023
 * @project TimeKeeping
 */
@Entity
@Table(name="WORK_RECORDS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkRecordEntity extends BasePersonEntity {

    @Id
    @Column(name = "WORK_RECORD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workRecordId;

    @Column(name = "WORKDAY_DETAIL_ID")
    private Integer workdayDetailId;

    @Column(name = "WORK_PLACE_ID")
    private Integer workPlaceId;

    @Column(name = "WORK_START_TIME_MORNING")
    private Timestamp workStartTimeMorning;

    @Column(name = "WORK_END_TIME_MORNING")
    private Timestamp workEndTimeMorning;

    @Column(name = "WORK_START_TIME_AFTERNOON")
    private Timestamp workStartTimeAfternoon;

    @Column(name = "WORK_END_TIME_AFTERNOON")
    private Timestamp workEndTimeAfternoon;

    @Column(name = "ABSENTED")
    @Builder.Default
    private Integer absentd = 0;

    @Column(name = "NOTE")
    private String note;
}
