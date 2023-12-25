package com.timekeeping.management.dto.scr005;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Src005Dto {
    private  String name;
    private  String employeeId;
    private  String startTime;
    private  String endTime;
    private  String sumTime;
    private  String status;
    private  String note;
}
