package com.timekeeping.common.repository;

import com.timekeeping.common.entity.WorkdayDetailEntity;
import com.timekeeping.management.dto.scr005.Src005DtoInterface;
import com.timekeeping.management.dto.scr009.Scr009DtoInterface;
import com.timekeeping.management.dto.src007.Src007DtoInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkdayDetailRepository extends JpaRepository<WorkdayDetailEntity, Integer> {

    WorkdayDetailEntity getWorkdayDetailEntitiesByWorkdayId(Integer wordDayId);

    WorkdayDetailEntity getWorkdayDetailEntityByEmployeeId(String employeeId);

    @Query(value = "SELECT * FROM USERS AS us INNER JOIN WORKDAY_DETAILS ON WORKDAY_DETAILS.userid = us.employeeId WHERE us.employeeId = :userId", nativeQuery = true)
    WorkdayDetailEntity getWorkdayDetailEntityByUserId(@Param("userId") Integer userId);

    /**
     * @param workDayId
     * @param employeeId
     */
    @Modifying
    @Query(value = "INSERT INTO dbo.WORKDAY_DETAILS (WORKDAY_ID, EMPLOYEE_ID) VALUES (:workDayId, :employeeId)", nativeQuery = true)
    void insertEmployeeByWorkDayAndUserID(@Param("workDayId") Integer workDayId, @Param("employeeId") String employeeId);


    @Query(value = "select u.Name, u.EMPLOYEE_ID as employeeId, wd.UPDATED_DT as updatedDt from [dbo].[WORKDAY_DETAILS] as wd " +
            "inner join [dbo].[WORKDAYS] as w on wd.WORKDAY_ID = w.WORKDAY_ID " +
            "inner join [dbo].[USERS] as u on u.EMPLOYEE_ID = wd.EMPLOYEE_ID " +
            "where w.WORKDAY_ID = :workdayId and w.WORK_PLACE_ID = :workPlaceId " +
            "order by wd.UPDATED_DT desc", nativeQuery = true)
    List<Scr009DtoInterface> getRecordInWorkDetail(@Param("workdayId") Integer workdayId, @Param("workPlaceId") Integer workPlaceId);

    /**
     * @param workDayId
     * @param employeeId
     */
    WorkdayDetailEntity getWorkdayDetailEntityByWorkdayIdAndEmployeeId(Integer workDayId, String employeeId);

    @Query(value = "select u.NAME, u.EMPLOYEE_ID as employeeId, wr.WORK_START_TIME_MORNING as startTimeMorning, wr.WORK_END_TIME_MORNING as endTimeMorning, " +
            "wr.WORK_START_TIME_AFTERNOON as startTimeAfternoon, wr.WORK_END_TIME_AFTERNOON as endTimeAfternoon, wr.NOTE as note " +
            "from [dbo].[WORKDAY_DETAILS] as wd " +
            "inner join [dbo].[WORKDAYS] as w on wd.WORKDAY_ID = w.WORKDAY_ID " +
            "inner join [dbo].[USERS] as u on u.EMPLOYEE_ID = wd.EMPLOYEE_ID " +
            "inner join [dbo].[WORK_RECORDS] as wr on wr.WORKDAY_DETAIL_ID = wd.WORKDAY_DETAIL_ID " +
            "where w.WORKDAY_ID = :workDayId and w.WORK_PLACE_ID = :workPlaceId " +
            "order by wd.UPDATED_DT desc", nativeQuery = true)
    List<Src005DtoInterface> getListUserByWorkDayAndWorkPlaceId(@Param("workDayId") Integer workDayId, @Param("workPlaceId") Integer workPlaceId);


    @Query(value = "SELECT wr.WORK_START_TIME_MORNING as startTimeMorning, wr.WORK_END_TIME_MORNING as endTimeMorning, " +
            "wr.WORK_START_TIME_AFTERNOON as startTimeAfternoon, " +
            "wr.WORK_END_TIME_AFTERNOON as endTimeAfternoon, qr.QR_CODE as qrcode, wd.NOTE as status, wr.NOTE as note " +
            "FROM [dbo].[WORKDAY_DETAILS] as wd " +
            "INNER JOIN [dbo].[WORK_RECORDS] as wr ON wr.WORKDAY_DETAIL_ID = wd.WORKDAY_DETAIL_ID " +
            "INNER JOIN [dbo].[QR_CODES] as qr ON qr.EMPLOYEE_ID = wd.EMPLOYEE_ID " +
            "WHERE wd.EMPLOYEE_ID = :employeeId " +
            "AND wd.WORKDAY_DETAIL_ID BETWEEN :startDay AND :endDay", nativeQuery = true)


    List<Src007DtoInterface> getListCheckTimeByEmployeeId(@Param("employeeId") String employeeId, @Param("startDay") Integer startDay, @Param("endDay") Integer endDay );


}
