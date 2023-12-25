//================================================================================
// ID: SRC009
// ProjectName: TIMEKEEPING
// SystemName: 勤怠管理システム
// ProgramName:QRチェックイン画面
// ClassName:Src009Form
//
// <<Modification History>>
// Version  | Date       | Updated By           | Content
// ---------+------------+----------------------+---------------------------------
// 01.00.00 | 2023/11/16 | Rikkei               | 新規作成
//================================================================================


package com.timekeeping.management.form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Src009Form {
    @NotEmpty(message = "qrcode cannot empty")
    private  String qrcode;
}
