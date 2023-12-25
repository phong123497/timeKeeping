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

package com.timekeeping.management.controller.scr009;
import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.exception.ApiError;
import com.timekeeping.management.dto.scr009.Src009ListDto;
import com.timekeeping.management.form.Src009Form;
import com.timekeeping.management.service.impl.Src009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/users")
@RestController
public class Src009Controller {

    @Autowired
    private Src009Service src009Service;
    /**ＱＲコードをゲット
     * @param form
     * @return responseEntity
     */
    @PostMapping("/src009/qrScan")
    public ResponseEntity<Src009ListDto> getQrcode(@Valid @RequestBody Src009Form form, BindingResult result) throws Exception {
        ResponseEntity<Src009ListDto> resRecord = null;
        if (result.hasErrors()) {
            ApiError error = new ApiError();
            error.setMessage(String.valueOf((result.getFieldError())));
            resRecord = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            Src009ListDto srcRes = src009Service.scanQR(form);
            try {
                resRecord = ResponseDataConfiguration.success(srcRes);
            } catch (Exception e) {
                ApiError error = new ApiError();
                error.setMessage("qrcode error");
                resRecord = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return resRecord;
    }

}
