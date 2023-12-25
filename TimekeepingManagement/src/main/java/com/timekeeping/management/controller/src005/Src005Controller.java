//================================================================================
// ID: SRC009
// ProjectName: TIMEKEEPING
// SystemName: 勤怠管理システム
// ProgramName:QRチェックイン画面
// ClassName:Src009Controller
//
// <<Modification History>>
// Version  | Date       | Updated By           | Content
// ---------+------------+----------------------+---------------------------------
// 01.00.00 | 2023/11/16 | Rikkei               | 新規作成
//================================================================================

package com.timekeeping.management.controller.src005;
import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.exception.ApiError;
import com.timekeeping.management.dto.scr005.Src005ListDto;
import com.timekeeping.management.form.Src005FormUpdateEmployee;
import com.timekeeping.management.service.impl.Src005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class Src005Controller {

    @Autowired
    private Src005Service src005Service;

    /** 従業員のリスクの日をゲット
     * @param workDay
     * @return List<Src005Entity>
     */
    @PostMapping("/list/employee/{workDay}")
    public ResponseEntity<Src005ListDto> getListUser(@PathVariable String workDay) throws Exception {
        Date workDate = null;
        ResponseEntity<Src005ListDto> resListUser = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            workDate = dateFormat.parse(workDay);

            Src005ListDto resList = src005Service.getListEmployee(workDate, 1);
            resListUser = ResponseDataConfiguration.success(resList);
        } catch (Exception e) {
            ApiError error = new ApiError();
            error.setMessage(e.getMessage());
            resListUser = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return resListUser;
    }

    /**
     *従業員の情報を更新
     * @param updateEmployee
     * @return
     * @throws Exception
     */
    @PostMapping("/update/employee")
    public ResponseEntity<Boolean> updateEmployeeTime(@RequestBody Src005FormUpdateEmployee updateEmployee) throws Exception {
        ResponseEntity<Boolean> resListUser ;
        System.out.println("startTime" +updateEmployee.getStartTime());
        System.out.println("endTime" +updateEmployee.getEndTime());
        try {
            resListUser = ResponseDataConfiguration.success(src005Service.updateTimeEmployee(updateEmployee));
        } catch (Exception e) {
            ApiError error = new ApiError();
            error.setMessage(e.getMessage());
            resListUser = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resListUser;
    }

}
