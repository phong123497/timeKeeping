package com.timekeeping.management.service.impl;


import com.google.zxing.WriterException;
import com.timekeeping.common.entity.CusMail;
import com.timekeeping.management.form.Src010FormEmployee;
import com.timekeeping.management.export.QrcodeExport;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;


    public static  final  String CREATE_SUCCESS = "アカウントを作成のお知らせ";
    public static final String MAIL_FORGOT_PASS = "MailForgotPass";
    public static  final  String SAVE_INFO_EMPLOYEE = "従業員の情報";
    public static  final  String FORGET_PASS_SUCCESS = "新しいパスワードがお知らせ！";


    // template mail
    public static final String MAIL_ACTIVE_COMPLETED = "MailActiveUserTemplate";
    public static final String MAIL_EMPLOYEE_INFO = "MailEmployeeInfo";
    public static final String MAIL_FORGET_PASS = "MailForgetPass";



    /**
     * send mail
     * @param cusMail
     * @param templateName
     * @throws MessagingException
     */
    public void sendMail(CusMail cusMail, String templateName) throws MessagingException{
        //which will allow you to set the email’s content as HTML with the setContent ()
        MimeMessage message = mailSender.createMimeMessage();
        // addAttachment email
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
        Context context = new Context();
        context.setVariables(cusMail.getPropertiesMail());
        String html = templateEngine.process(templateName, context);
        helper.setTo(cusMail.getToMailAddress());
        if (cusMail.getToCCAddress()!=null){
            helper.setCc(cusMail.getToCCAddress());
        }
        helper.setSubject(cusMail.getSubjectMail());
        helper.setText(html, true);
        mailSender.send(message);
    }

    public  CusMail saveEmployeeInfo (String[]  toMailAddress, Src010FormEmployee employee) throws IOException, WriterException {
        CusMail cusMail = new CusMail();
        cusMail.setToMailAddress(toMailAddress);
        cusMail.setSubjectMail(SAVE_INFO_EMPLOYEE);
        byte[] qrcodeImage = QrcodeExport.getQRCodeImage(employee.getQrcode());
        String base64Image = Base64.encodeBase64String(qrcodeImage);
        Map<String,Object>  propertiesMail = new HashMap<>();
        propertiesMail.put("employeeId", employee.getEmployeeId());
        propertiesMail.put("name", employee.getName());
        propertiesMail.put("email", employee.getEmail());
        propertiesMail.put("phone", employee.getPhone());
        propertiesMail.put("qrcode", employee.getQrcode());
        propertiesMail.put("qrcodeImage", base64Image);
        cusMail.setPropertiesMail(propertiesMail);
        return  cusMail;
    }

    public CusMail sendForgetPass(String[]  toMailAddress, String passWord) throws IOException, WriterException {
        CusMail cusMail = new CusMail();
//        cusMail.setToMailAddress(toMailAddress);
        cusMail.setSubjectMail(FORGET_PASS_SUCCESS);
        Map<String, Object> propertyMail= new HashMap<>();
        propertyMail.put("newPass", passWord);
        propertyMail.put("email", toMailAddress[0]);
        cusMail.setPropertiesMail(propertyMail);
        return cusMail;
    }
}
