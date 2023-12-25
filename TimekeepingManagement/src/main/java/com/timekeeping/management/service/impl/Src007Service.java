package com.timekeeping.management.service.impl;

import com.timekeeping.common.entity.WorkdayEntity;
import com.timekeeping.common.repository.*;
import com.timekeeping.management.dto.src007.Src007Dto;
import com.timekeeping.management.dto.src007.Src007DtoInterface;
import com.timekeeping.management.dto.src007.Src007ListDto;
import com.timekeeping.management.form.Src007Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Src007Service {

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

    public Src007ListDto getListCheckTimeByEmployee(Src007Form src007Form) throws Exception{
        String startDay = src007Form.getStartDay();
        String endDay = src007Form.getEndDay();
        WorkdayEntity workdayEntityStart = workDayRepository.getWorkdayEntityByWorkdayCus(startDay);
        WorkdayEntity workdayEntityEnd = workDayRepository.getWorkdayEntityByWorkdayCus(endDay);
        Src007ListDto listCheckedTime = new Src007ListDto();
        List<Src007Dto> listTimeCheckEmployee = new ArrayList<>();
        if(workdayEntityStart == null || workdayEntityEnd == null ){
            return  listCheckedTime;
        }else {
            List<Src007DtoInterface> listTimeChecked = workdayDetailRepository.getListCheckTimeByEmployeeId(src007Form.getEmployeeId(),
                    workdayEntityStart.getWorkdayId(),workdayEntityEnd.getWorkdayId());
            // get data from workdayDetailRepository
            listTimeChecked.forEach(timeChecked ->{
                Src007Dto resEntity= new Src007Dto();
                if(timeChecked.getStartTimeMorning()!=null && timeChecked.getEndTimeAfternoon()!=null){
                    resEntity.setStartTime(timeChecked.getStartTimeMorning());
                    resEntity.setEndTime(timeChecked.getEndTimeAfternoon());
                } else if (timeChecked.getStartTimeMorning()!=null && timeChecked.getEndTimeMorning()!=null) {
                    resEntity.setStartTime(timeChecked.getStartTimeMorning());
                    resEntity.setEndTime(timeChecked.getEndTimeMorning());
                } else if (timeChecked.getStartTimeAfternoon()!=null && timeChecked.getEndTimeAfternoon()!=null) {
                    resEntity.setStartTime(timeChecked.getStartTimeAfternoon());
                    resEntity.setEndTime(timeChecked.getEndTimeAfternoon());
                }
                resEntity.setNote(timeChecked.getNote());
                resEntity.setStatus(timeChecked.getStatus());
                resEntity.setQrcode(timeChecked.getQrcode());
                listTimeCheckEmployee.add(resEntity);
            });
            // get count onTime and late in month
            Integer countOnTimeInMonth = recordRepository.getCountInComeInMonth(src007Form.getEmployeeId(), workdayEntityStart.getWorkdayId(),workdayEntityEnd.getWorkdayId());
            Integer countLateInMonth = recordRepository.getCountLateInMonth(src007Form.getEmployeeId(), workdayEntityStart.getWorkdayId(),workdayEntityEnd.getWorkdayId());
            listCheckedTime.setOnTime(countOnTimeInMonth);
            listCheckedTime.setLate(countLateInMonth);

            listCheckedTime.setListTimeChecked(listTimeCheckEmployee);

        }
        return listCheckedTime;
    }


}
