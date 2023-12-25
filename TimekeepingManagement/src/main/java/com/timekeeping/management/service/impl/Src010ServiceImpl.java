package com.timekeeping.management.service.impl;

import com.google.zxing.WriterException;
import com.timekeeping.common.entity.CusMail;
import com.timekeeping.common.entity.QrCodeEntity;
import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.repository.QrCodeRepository;
import com.timekeeping.common.repository.UserRepository;
import com.timekeeping.management.config.SenderMailEvent;
import com.timekeeping.management.form.Src010FormEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.timekeeping.management.service.impl.MailServiceImpl.MAIL_EMPLOYEE_INFO;

@Service
public class Src010ServiceImpl {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private MailServiceImpl mailService;

    /**
     * 従業員の情報を更新関数
     * @param updateEmployee
     * @return
     */

    public Boolean updateEmployee(Src010FormEmployee updateEmployee) {
        // check user is exist
        UserEntity exitsUser = userRepository.getUserEntityByEmployeeId(updateEmployee.getEmployeeId());
        if (exitsUser != null) {
            if (updateEmployee.getName() != null) {
                exitsUser.setName(updateEmployee.getName());
            } else if (updateEmployee.getEmail() != null) {
                exitsUser.setEmail(updateEmployee.getEmail());
            } else if (updateEmployee.getPhone() != null) {
                exitsUser.setPhoneNumber(updateEmployee.getPhone());
            }
            userRepository.save(exitsUser);
            return true;
        } else {
            return false;
        }
    }
    /**
     * メールに従業員の情報を送信
     * @param updateEmployee
     * @return
     */

    public Boolean saveToMailEmployee(Src010FormEmployee updateEmployee)  {
        // check user is exist
        UserEntity exitsUser = userRepository.getUserEntityByEmployeeId(updateEmployee.getEmployeeId());
        if (exitsUser != null) {
            // getQrcode
            QrCodeEntity qrCodeEntity = qrCodeRepository.getQrCodeEntityByEmployeeIdAndDeletedFg(exitsUser.getEmployeeId(),0);
            updateEmployee.setQrcode(qrCodeEntity.getQrCode());
            //create event
            CusMail employeeInfo = null;
            try {
                employeeInfo = mailService.saveEmployeeInfo(new String[]{updateEmployee.getEmail()},updateEmployee );
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
            applicationEventPublisher.publishEvent(new SenderMailEvent(this, employeeInfo,MAIL_EMPLOYEE_INFO));
            return true;
        } else {
            return false;
        }
    }


}
