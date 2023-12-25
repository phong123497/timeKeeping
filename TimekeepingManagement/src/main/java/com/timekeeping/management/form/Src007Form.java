package com.timekeeping.management.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Src007Form {
    @NotNull(message = "employeeId not null")
    private  String  employeeId;
    @NotNull(message = "startDay not null")
    private  String startDay;
    @NotNull(message = "endDay not null")
    private  String endDay;
}
