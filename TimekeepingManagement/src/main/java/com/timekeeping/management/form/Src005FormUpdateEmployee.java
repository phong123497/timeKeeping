package com.timekeeping.management.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Src005FormUpdateEmployee {

    private String employeeId;
    private String startTime;
    private String endTime;
    private String status;
    private String note;
    private String date;
}
