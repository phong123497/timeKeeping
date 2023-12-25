package com.timekeeping.management.dto.src007;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Src007ListDto {

    private List<Src007Dto> listTimeChecked;
    private Integer onTime;
    private Integer late;
    private Integer noAttend;
}
