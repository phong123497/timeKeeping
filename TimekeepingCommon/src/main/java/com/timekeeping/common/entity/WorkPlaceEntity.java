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
@Table(name="WORK_PLACES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceEntity extends BasePersonEntity {

    @Id
    @Column(name = "WORK_PLACE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workPlaceId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARENT_WORK_PLACE_ID")
    private Integer parentWorkPlaceId;

    @Column(name = "WORKTIME_ID")
    private Integer worktimeId;

    @Column(name = "DELETED_FG")
    @Builder.Default
    private Integer deletedFg = 0;
}
