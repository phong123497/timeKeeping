package com.timekeeping.common.repository;

import com.timekeeping.common.entity.WorkRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRecordRepository  extends JpaRepository <WorkRecordEntity,Integer> {

    WorkRecordEntity getWorkRecordEntityByAndWorkdayDetailId(Integer workdayDetailId );

    /**
     *
     * @param workDayId
     * @param startMorning
     * @param startAfternoon
     * @return
     */
    @Query(value = "SELECT count(*) FROM [dbo].[WORK_RECORDS] as wr " +
            "INNER JOIN [dbo].[WORKDAY_DETAILS] as wd ON wd.WORKDAY_DETAIL_ID = wr.WORKDAY_DETAIL_ID " +
            "INNER JOIN [dbo].[WORKDAYS] as w ON w.WORKDAY_ID = wd.WORKDAY_ID " +
            "WHERE w.WORKDAY_ID = :workDayId " +
            "AND (wr.WORK_START_TIME_MORNING < :startMorning or wr.WORK_START_TIME_AFTERNOON < :startAfternoon)", nativeQuery = true)
    Integer getCountOnTimeInDay(@Param("workDayId") Integer workDayId, @Param("startMorning") String startMorning, @Param("startAfternoon") String startAfternoon);

    /**
     * getCountLate
     * @param workDayId
     * @param startMorning
     * @param startAfternoon
     * @return
     */

    @Query(value = "SELECT count(*) FROM [dbo].[WORK_RECORDS] as wr " +
            "INNER JOIN [dbo].[WORKDAY_DETAILS] as wd ON wd.WORKDAY_DETAIL_ID = wr.WORKDAY_DETAIL_ID " +
            "INNER JOIN [dbo].[WORKDAYS] as w ON w.WORKDAY_ID = wd.WORKDAY_ID " +
            "WHERE w.WORKDAY_ID = :workDayId " +
            "AND (wr.WORK_START_TIME_MORNING > :startMorning or wr.WORK_START_TIME_AFTERNOON > :startAfternoon)", nativeQuery = true)
    Integer getCountLateInDay(@Param("workDayId") Integer workDayId, @Param("startMorning") String startMorning, @Param("startAfternoon") String startAfternoon);


    /**
     *
     * @param employeeId   //
     * @param startDay    //
     * @param endDay
     * @return
     */
    @Query(value = "SELECT COUNT(*) " +
            "FROM [dbo].[WORKDAY_DETAILS] as wd " +
            "INNER JOIN [dbo].[WORK_RECORDS] as wr ON wr.WORKDAY_DETAIL_ID = wd.WORKDAY_DETAIL_ID " +
            "WHERE " +
            "(wr.WORK_START_TIME_MORNING < '09:00:00' OR wr.WORK_START_TIME_AFTERNOON > '12:00:00' AND wr.WORK_START_TIME_AFTERNOON < '13:00:00') " +
            "AND wd.EMPLOYEE_ID = :employeeId " +
            "AND wd.WORKDAY_DETAIL_ID BETWEEN :startDay AND :endDay", nativeQuery = true)
    Integer getCountInComeInMonth(@Param("employeeId") String employeeId, @Param("startDay") Integer startDay, @Param("endDay") Integer endDay);

    /**
     * gte count late in month
     * @param employeeId
     * @param startDay
     * @param endDay
     * @return
     */
    @Query(value = "SELECT COUNT(*) " +
            "FROM [dbo].[WORKDAY_DETAILS] as wd " +
            "INNER JOIN [dbo].[WORK_RECORDS] as wr ON wr.WORKDAY_DETAIL_ID = wd.WORKDAY_DETAIL_ID " +
            "WHERE " +
            "(wr.WORK_START_TIME_MORNING BETWEEN '09:00:00' AND '12:00:00' OR wr.WORK_START_TIME_AFTERNOON > '13:00:00') " +
            "AND wd.EMPLOYEE_ID = :employeeId " +
            "AND wd.WORKDAY_DETAIL_ID BETWEEN :startDay AND :endDay", nativeQuery = true)
    Integer getCountLateInMonth(@Param("employeeId") String employeeId, @Param("startDay") Integer startDay, @Param("endDay") Integer endDay);




}
