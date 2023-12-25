package com.timekeeping.common.entity;

import com.timekeeping.common.component.BasePersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author minhtq2 on 02/11/2023
 * @project TimeKeeping
 */
@Entity
@Table(name="MST_WORKTIMES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MstWorktimeEntity extends BasePersonEntity {
    @Id
    @Column(name = "WORKTIME_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer worktimeId;

    @Column(name = "START_TIME")
    private Timestamp startTime;

    @Column(name = "END_TIME")
    private Timestamp endTime;

    @Column(name = "REST_START_TIME")
    private Timestamp restStartTime;

    @Column(name = "REST_END_TIME")
    private Timestamp restEndTime;

    @Column(name = "START_BEFORE")
    private Integer startBefore;

    @Column(name = "DELETED_FG")
    @Builder.Default
    private Integer deletedFg = 0;
}
