package com.timekeeping.common.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QrcodeDto {

    @NotNull(message = "qrcode cannot null")
    private String qrCodeName;
}
