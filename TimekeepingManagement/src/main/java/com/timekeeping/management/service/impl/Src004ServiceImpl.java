package com.timekeeping.management.service.impl;

import com.google.zxing.WriterException;
import com.timekeeping.common.entity.CusMail;
import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.repository.UserRepository;
import com.timekeeping.management.config.SenderMailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

import static com.timekeeping.management.service.impl.MailServiceImpl.MAIL_FORGET_PASS;

@Service
public class Src004ServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public Boolean forgetPass(String email) throws IOException, WriterException {

        //check user is exist
        UserEntity userEntity = userRepository.getUserEntityByUsername(email);
        if(userEntity!=null){
            String newPass= generateNewPassWord();
            userEntity.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(userEntity);
            // send mail
            CusMail employeeEmail = mailService.sendForgetPass(new String[]{email},newPass);
            applicationEventPublisher.publishEvent(new SenderMailEvent(this, employeeEmail,MAIL_FORGET_PASS));
            return  true;
        }else {
            return false;

        }
    }

    public String generateNewPassWord() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random random = new Random();
        StringBuilder newPass = new StringBuilder();
        // create qrcode has length <10
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(charSet.length());
            newPass.append(charSet.charAt(index));
        }
        return newPass.toString();
    }
}
