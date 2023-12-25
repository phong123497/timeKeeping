package com.timekeeping.common.repository;

import com.timekeeping.common.entity.WorkdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkdayEntity,Integer> {

    WorkdayEntity getWorkdayEntityByWorkdayId (Integer workPlaceId  );
    @Query(value = "select * from [dbo].[WORKDAYS]  as w  where  CAST(w.WORKDAY AS DATE) = :wordDay", nativeQuery = true )
    WorkdayEntity getWorkdayEntityByWorkdayCus(String wordDay);





}
