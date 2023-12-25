//================================================================================
// ID: SRC009
// ProjectName: TIMEKEEPING
// SystemName: 勤怠管理システム
// ProgramName:QRチェックイン画面
// ClassName:Scr009ScanListItem
//
// <<Modification History>>
// Version  | Date       | Updated By           | Content
// ---------+------------+----------------------+---------------------------------
// 01.00.00 | 2023/11/16 | Rikkei               | 新規作成
//================================================================================


package com.timekeeping.management.dto.scr009;
import java.sql.Timestamp;

public interface Scr009DtoInterface {
     String getName();
     String getEmployeeId();
     Timestamp getUpdatedDt();

}
