package com.timekeeping.management.controller.src004;


import com.google.zxing.WriterException;
import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.exception.ApiError;
import com.timekeeping.management.service.impl.Src004ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Src004Controller {
    @Autowired
    private Src004ServiceImpl src004Service;

    /**
     * メールに新しいパスワードを送信
     * @param email
     * @return
     * @throws IOException
     * @throws WriterException
     */
    @PostMapping("/forget/pass{email}")
    public ResponseEntity<Boolean> forgetPassSendMail(@PathVariable String email) throws IOException, WriterException {
        ResponseEntity<Boolean> resResult= null;
        if(email!=null){
            Boolean results = src004Service.forgetPass(email);
            resResult =  ResponseDataConfiguration.success(results);
        }else {
            ApiError error = new ApiError();
            error.setMessage("email cannot null");
            resResult = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resResult;
    }


}
