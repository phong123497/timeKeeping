package com.timekeeping.management.form;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Src010FormEmployee {
    @NotEmpty(message = "従業員のIDはブラックが出来ない！")
    private String employeeId;
    @NotEmpty(message = "従業員の氏名はブラックが出来ない！")
    private String name;
    @NotEmpty(message = "従業員のメールはブラックが出来ない！")
    private String email;
    @NotEmpty(message = "従業員の携帯はブラックが出来ない！")
    private String phone;
    private String qrcode;

}
