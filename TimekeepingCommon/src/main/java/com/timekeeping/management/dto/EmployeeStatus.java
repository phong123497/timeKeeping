package com.timekeeping.management.dto;


import lombok.Getter;

@Getter
public enum EmployeeStatus {
    ONTIME("Bình thường"),
    LATE("Đi muộn"),
    NO_ATATTEND("Nghỉ phép"),
    SCAN_ERROR("Lỗi");

    String name;

    EmployeeStatus(String name) {
        this.name = name;
    }


}
