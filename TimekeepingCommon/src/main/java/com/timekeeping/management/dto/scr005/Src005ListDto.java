package com.timekeeping.management.dto.scr005;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Src005ListDto {

    private  List<Src005Dto> listEntity ;
    private  Integer onTime;
    private  Integer late;
    private  Integer noAttend ;
}
