package com.timekeeping.management.service.impl;


import com.timekeeping.common.entity.WorkRecordEntity;
import com.timekeeping.common.entity.WorkdayDetailEntity;
import com.timekeeping.common.entity.WorkdayEntity;
import com.timekeeping.common.repository.*;
import com.timekeeping.management.dto.EmployeeStatus;
import com.timekeeping.management.dto.scr005.Src005Dto;
import com.timekeeping.management.dto.scr005.Src005DtoInterface;
import com.timekeeping.management.dto.scr005.Src005ListDto;
import com.timekeeping.management.form.Src005FormUpdateEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class Src005Service {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkdayDetailRepository workdayDetailRepository;
    @Autowired
    private WorkRecordRepository recordRepository;

    @Autowired
    private MstWorkTimeRepository mstWorkTimeRepository;

    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private ApplicationEventPublisher applicationEvent;
    private static final String START_TIME = "09:00:00";
    private static final String REST_END_TIME = "13:00:00";
    private static final String END_TIME = "18:00:00";

    public Src005ListDto getListEmployee(Date workDay, Integer workPlaceId) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        WorkdayEntity workdayEntity = workDayRepository.getWorkdayEntityByWorkdayCus(format.format(workDay));

//        MstWorktimeEntity mstWorktime = mstWorkTimeRepository.getMstWorktimeEntityByWorktimeId(workdayEntity.getWorkPlaceId());
//        String getStart = timeFormat.format(mstWorktime.getStartTime());
//        String getRestEndTime = timeFormat.format(mstWorktime.getRestEndTime());
//        String getEndTime = timeFormat.format(mstWorktime.getEndTime());
//        Date start= timeFormat.parse("09:00:00");
//        Date end= timeFormat.parse("18:00:00");
        Src005ListDto resListUser = new Src005ListDto();
        List<Src005Dto> entityList = new ArrayList<>();
        if (workdayEntity == null) {
            resListUser = new Src005ListDto();
        } else {

            List<Src005DtoInterface> listDataUser = workdayDetailRepository.getListUserByWorkDayAndWorkPlaceId(workdayEntity.getWorkdayId(), 1);
            listDataUser.forEach(user -> {

                Src005Dto resEntity = new Src005Dto();
                String startTimeMorning = (user.getStartTimeMorning() != null) ? timeFormat.format(user.getStartTimeMorning()) : "";

                String endTimeMorning = (user.getEndTimeMorning() != null) ? timeFormat.format(user.getEndTimeMorning()) : "";
                String startTimeAfternoon = (user.getStartTimeAfternoon() != null) ? timeFormat.format(user.getStartTimeAfternoon()) : "";
                String endTimeAfternoon = (user.getEndTimeAfternoon() != null) ? timeFormat.format(user.getEndTimeAfternoon()) : "";
                // set startTime
                resEntity = setStartTime(resEntity, startTimeMorning, endTimeMorning, startTimeAfternoon, endTimeAfternoon);
                //set end time for user
                try {
                    resEntity = setEndTime(user, resEntity, startTimeMorning, endTimeMorning, startTimeAfternoon, endTimeAfternoon);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                resEntity.setName(user.getName());
                resEntity.setEmployeeId(user.getEmployeeId());
                resEntity.setNote(user.getNote());
                entityList.add(resEntity);
            });
            //get all user
            Integer countUser = userRepository.getCountUser();
            //get all onTime
            Integer countOnTime = recordRepository.getCountOnTimeInDay(workdayEntity.getWorkdayId(), START_TIME, REST_END_TIME);
            resListUser.setOnTime(countOnTime);

            //get all late
            Integer countLate = recordRepository.getCountLateInDay(workdayEntity.getWorkdayId(), START_TIME, REST_END_TIME);
            resListUser.setLate(countLate);
            // notAttend
            resListUser.setNoAttend(countUser - countOnTime - countLate);

            resListUser.setListEntity(entityList);
        }
        return resListUser;
    }

    public String getSumTime(Date startTime, Date endTime) throws Exception {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("startTime and endTime cannot be null");
        }
        if (startTime.before(timeFormat.parse(START_TIME))) {
            startTime = timeFormat.parse(START_TIME);
        } else if (startTime.after(timeFormat.parse("12:00:00")) && startTime.after(timeFormat.parse(REST_END_TIME))) {
            startTime = timeFormat.parse(REST_END_TIME);
        }
        LocalTime localEnd = LocalTime.of(endTime.getHours(), endTime.getMinutes(), endTime.getSeconds());
        LocalTime localStart = LocalTime.of(startTime.getHours(), startTime.getMinutes(), startTime.getSeconds());
        Duration duration = Duration.between(localStart, localEnd);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return String.format("%d:%d", hours, minutes);
    }

    public Src005Dto setStartTime(Src005Dto resEntity, String startTimeMorning, String endTimeMorning, String startTimeAfternoon, String endTimeAfternoon) {
        if (!startTimeMorning.isEmpty() && !endTimeMorning.isEmpty() && startTimeAfternoon.isEmpty()) {
            // nếu <9:00 xét 9:00
            resEntity.setStartTime(startTimeMorning);
            if (startTimeMorning.compareTo(START_TIME) < 0) {
                resEntity.setStatus(EmployeeStatus.ONTIME.getName());
            } else if (startTimeMorning.compareTo(START_TIME) > 0) {
                resEntity.setStatus(EmployeeStatus.LATE.getName());
            }
        } else if (startTimeMorning.isEmpty() && endTimeMorning.isEmpty() && !startTimeAfternoon.isEmpty()) {
            resEntity.setStartTime(startTimeAfternoon);
            if (startTimeAfternoon.compareTo(REST_END_TIME) < 0) {
                resEntity.setStatus(EmployeeStatus.ONTIME.getName());
            } else if (startTimeAfternoon.compareTo(REST_END_TIME) > 0) {
                resEntity.setStatus(EmployeeStatus.LATE.getName());
            }

        } else if (startTimeMorning.isEmpty() && endTimeMorning.isEmpty() && startTimeAfternoon.isEmpty() && endTimeAfternoon.isEmpty()) {
            resEntity.setStartTime("");
        }
        else {
            resEntity.setStartTime(START_TIME);
        }
        return resEntity;
    }

    /**
     * set endTime, chưa xét đến các trường hợp cố ý check lỗi
     * còn trường hợp endTimeMorning !=null  và endTimeAfternoon != null (scan 2 lượt  )
     *
     * @param user
     * @param resEntity
     * @param startTimeMorning
     * @param endTimeMorning
     * @param startTimeAfternoon
     * @param endTimeAfternoon
     * @return
     * @throws Exception
     */
    public Src005Dto setEndTime(Src005DtoInterface user, Src005Dto resEntity, String startTimeMorning, String endTimeMorning, String startTimeAfternoon, String endTimeAfternoon) throws Exception {
        String sumTime = null;
        if (!startTimeMorning.isEmpty() && !endTimeAfternoon.isEmpty()) {
            resEntity.setEndTime(endTimeAfternoon);
            sumTime = getSumTime(user.getStartTimeMorning(), user.getEndTimeAfternoon());
            resEntity.setSumTime(sumTime);
        } else if (!startTimeMorning.isEmpty() && !endTimeMorning.isEmpty() && endTimeAfternoon.isEmpty()) {
            resEntity.setEndTime(endTimeMorning);
            sumTime = getSumTime(user.getStartTimeMorning(), user.getEndTimeMorning());
            resEntity.setSumTime(sumTime);
        } else if (startTimeMorning.isEmpty() && endTimeMorning.isEmpty() && !startTimeAfternoon.isEmpty() && !endTimeAfternoon.isEmpty()) {
            resEntity.setEndTime(endTimeAfternoon);
            sumTime = getSumTime(user.getStartTimeAfternoon(), user.getEndTimeAfternoon());
            resEntity.setSumTime(sumTime);
        } else if (startTimeMorning.isEmpty() && endTimeMorning.isEmpty() && startTimeAfternoon.isEmpty() && endTimeAfternoon.isEmpty()) {
            resEntity.setEndTime("");
            resEntity.setSumTime("");
        }else{
                resEntity.setEndTime("18:00:00");
                sumTime = getSumTime(user.getStartTimeMorning(), user.getEndTimeAfternoon());
                resEntity.setSumTime(sumTime);
        }
        return resEntity;
    }


    public boolean updateTimeEmployee(Src005FormUpdateEmployee updateEmployee) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        //get workdayID from Src005UpdateEmployees
        Date startTimeDay = timeFormat.parse("07:00:00");
        Date resTimeDay = timeFormat.parse("13:00:00");
        Date endTimeDay = timeFormat.parse("22:00:00");
        Date editStartTime = timeFormat.parse(updateEmployee.getStartTime());
        Date editEndTimeLocal = timeFormat.parse(updateEmployee.getEndTime());
        Timestamp saveTimeStart = new Timestamp(editStartTime.getTime());
        Timestamp saveTimeEnd = new Timestamp(editEndTimeLocal.getTime());

        WorkdayEntity workdayEntity = workDayRepository.getWorkdayEntityByWorkdayCus(updateEmployee.getDate());
        if (workdayEntity != null) {
            // check employeeId is exits
            WorkdayDetailEntity workdayDetail = workdayDetailRepository.getWorkdayDetailEntityByWorkdayIdAndEmployeeId(workdayEntity.getWorkdayId(), updateEmployee.getEmployeeId());
            if (workdayDetail != null) {

                workdayDetail.setNote(updateEmployee.getStatus());
                workDayRepository.save(workdayEntity);

                WorkRecordEntity updateRecord = recordRepository.getWorkRecordEntityByAndWorkdayDetailId(workdayDetail.getWorkdayDetailId());
                Timestamp getStartTimeMo = updateRecord.getWorkStartTimeMorning();
                Timestamp getEndTimeMo = updateRecord.getWorkEndTimeMorning();
                Timestamp getStartTimeAf = updateRecord.getWorkStartTimeAfternoon();
                Timestamp getEndTimeAf = updateRecord.getWorkEndTimeAfternoon();

                updateRecord.setNote(updateEmployee.getNote());
                // set startTime
                /**
                 *  nhận giá trị đầu vào là startTime và End Time xét giá trị và fill và các trường trong workRecord
                 *  chia thời gian trong ngày khi lấy giá trị từ updateEmployee  ,lấy  13h làm mốc xác định thời gian
                 *  lấy buổi sáng 7-13 chiều tư 13 -22
                 */
                // 7h - 13h
                if (editStartTime.after(startTimeDay) && editEndTimeLocal.before(resTimeDay)) {
                    if (getStartTimeMo != null && getEndTimeAf != null) {
                        updateRecord.setWorkStartTimeMorning(saveTimeStart);
                        updateRecord.setWorkEndTimeMorning(saveTimeEnd);
                        updateRecord.setWorkEndTimeAfternoon(null);
                    } else if (getStartTimeMo != null && getEndTimeMo != null) {
                        updateRecord.setWorkStartTimeMorning(saveTimeStart);
                        updateRecord.setWorkEndTimeMorning(saveTimeEnd);
                    } else if (getStartTimeAf != null && getEndTimeAf != null) {
                        updateRecord.setWorkStartTimeMorning(saveTimeStart);
                        updateRecord.setWorkEndTimeMorning(saveTimeEnd);
                        updateRecord.setWorkStartTimeAfternoon(null);
                        updateRecord.setWorkEndTimeAfternoon(null);
                    }
                    // 13-22 h
                } else if (editStartTime.after(resTimeDay) && editEndTimeLocal.before(endTimeDay)) {
                    if (getStartTimeMo != null && getEndTimeAf != null) {
                        updateRecord.setWorkStartTimeMorning(null);
                        updateRecord.setWorkEndTimeAfternoon(saveTimeStart);
                        updateRecord.setWorkEndTimeMorning(saveTimeEnd);
                    } else if (getStartTimeMo != null && getEndTimeMo != null) {
                        updateRecord.setWorkStartTimeAfternoon(saveTimeStart);
                        updateRecord.setWorkEndTimeAfternoon(saveTimeEnd);
                        updateRecord.setWorkStartTimeMorning(null);
                        updateRecord.setWorkEndTimeMorning(null);

                    } else if (getStartTimeAf != null && getEndTimeAf != null) {
                        updateRecord.setWorkStartTimeAfternoon(saveTimeStart);
                        updateRecord.setWorkEndTimeAfternoon(saveTimeEnd);
                    }
                    //7-22
                } else if (editStartTime.after(startTimeDay) && editEndTimeLocal.before(endTimeDay)) {
                    if (getStartTimeMo != null && getEndTimeAf != null) {
                        updateRecord.setWorkStartTimeMorning(saveTimeStart);
                        updateRecord.setWorkEndTimeAfternoon(saveTimeEnd);
                    } else if (getStartTimeMo != null && getEndTimeMo != null) {
                        updateRecord.setWorkStartTimeMorning(saveTimeStart);
                        updateRecord.setWorkEndTimeMorning(null);
                        updateRecord.setWorkEndTimeAfternoon(saveTimeEnd);
                    } else if (getStartTimeAf != null && getEndTimeAf != null) {
                        updateRecord.setWorkStartTimeAfternoon(null);
                        updateRecord.setWorkStartTimeMorning(saveTimeStart);
                        updateRecord.setWorkEndTimeAfternoon(saveTimeEnd);
                    }
                }
                recordRepository.save(updateRecord);

            } else {
                return false;
            }
        } else {
            return false;
        }
        return  true;
    }

}
