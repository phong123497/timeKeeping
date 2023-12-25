package com.timekeeping.management.controller.src010;

import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.exception.ApiError;
import com.timekeeping.management.form.Src010FormEmployee;
import com.timekeeping.management.export.Src010EmployeeExcel;
import com.timekeeping.management.service.impl.Src010ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class Src010Controller {

    @Autowired
    private Src010ServiceImpl src010Service;

    @Autowired
    private Src010EmployeeExcel src010EmployeeExcel;

    private static final String FILE_NAME = "従業員の情報";
    /**従業員の情報を更新
     * @param updateEmployee
     * @return boolean
     */
    @PostMapping("/src010/update")
    public ResponseEntity<Boolean> updateEmployee(@Valid  @RequestBody Src010FormEmployee updateEmployee , BindingResult result) {
        ResponseEntity<Boolean> resResults = null;
        if(result.hasErrors()){
            ApiError error = new ApiError();
            error.setMessage(String.valueOf((result.getFieldError())));
            resResults = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            try {
                Boolean resRes = src010Service.updateEmployee(updateEmployee);
                resResults = ResponseDataConfiguration.success(resRes);
            } catch (Exception e) {
                ApiError error = new ApiError();
                error.setMessage(e.getMessage());
                resResults = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return resResults;
    }

    /**
     * 端末に保存する従業員の情報
     * @param employee
     * @return
     */
    @PostMapping("/src010/local/save")
    public ResponseEntity<?> saveToDevice(@RequestBody Src010FormEmployee employee) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        ResponseEntity<?> response = null;
        try {
            String encodedFilename = URLEncoder.encode(FILE_NAME + currentDateTime, "UTF-8");
            String filename = encodedFilename + ".xlsx";
            HttpHeaders headers = new HttpHeaders();
            // use APPLICATION_OCTET_STREAM  when file download and binary data
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //create header
            headers.setContentDispositionFormData(filename, filename);
            byte[] resource = src010EmployeeExcel.employeeExportExcel(employee);
            response = new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            ApiError error = new ApiError();
            error.setMessage(e.getMessage());
            response = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * メールに従業員の情報を送信
     * @param updateEmployee
     * @param result
     * @return
     */
    @PostMapping("/src010/mail/save")
    public ResponseEntity<Boolean> saveToMail(@Valid @RequestBody Src010FormEmployee updateEmployee, BindingResult result) {

        ResponseEntity<Boolean> resResults = null;
        if(result.hasErrors()){
            ApiError error = new ApiError();
            error.setMessage(String.valueOf((result.getFieldError())));
            resResults = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            try {
                Boolean resRes = src010Service.saveToMailEmployee(updateEmployee);
                resResults = ResponseDataConfiguration.success(resRes);
            } catch (Exception e) {
                ApiError error = new ApiError();
                error.setMessage(e.getMessage());
                resResults = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return resResults;
    }


}

