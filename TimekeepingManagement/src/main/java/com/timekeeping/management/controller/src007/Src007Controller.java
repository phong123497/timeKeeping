//================================================================================
// ID: SRC007
// ProjectName: TIMEKEEPING
// SystemName: 勤怠管理システム
// ProgramName:従業員詳細
// ClassName:Src007Controller
//
// <<Modification History>>
// Version  | Date       | Updated By           | Content
// ---------+------------+----------------------+---------------------------------
// 01.00.00 | 2023/04/12 | Rikkei               | 新規作成
//================================================================================

package com.timekeeping.management.controller.src007;


import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.exception.ApiError;

import com.timekeeping.management.dto.src007.Src007ListDto;
import com.timekeeping.management.export.Src007EmployeeExport;
import com.timekeeping.management.form.Src007Form;
import com.timekeeping.management.service.impl.Src007Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class Src007Controller {
    @Autowired
    private Src007Service src007Service;

    @Autowired
    private Src007EmployeeExport employeeExport;
    private static final String FILE_NAME = "従業員のチェックタイム詳細";

    /**
     * 従業員のチェックタイム詳細ゲット
     * @param employee
     * @return
     * @throws Exception
     */
    @PostMapping("/src007/employee/detail")
    public ResponseEntity<Src007ListDto> getDetailEmployee(@RequestBody Src007Form employee) throws Exception {
        ResponseEntity<Src007ListDto> resListTimeCheck = null;

        try {
            Src007ListDto resList = src007Service.getListCheckTimeByEmployee(employee);
            resListTimeCheck = ResponseDataConfiguration.success(resList);
        } catch (Exception e) {
            ApiError error = new ApiError();
            error.setMessage(e.getMessage());
            resListTimeCheck = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resListTimeCheck;
    }

    /**
     * 従業員がエクセルエクスポート
     * @param employee
     * @return
     * @throws Exception
     */
    @PostMapping("/src007/employee/excel")
    public ResponseEntity<?> exportEmployee(@RequestBody Src007Form employee) throws Exception {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        ResponseEntity<?> response = null;

        try {
            String encodedFilename = URLEncoder.encode(FILE_NAME + currentDateTime, "UTF-8");
            String filename = encodedFilename + ".xlsx";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData(filename, filename);
            byte[] resource = employeeExport.exportExcel(employee);
            response = new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            ApiError error = new ApiError();
            error.setMessage(e.getMessage());
            response = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
