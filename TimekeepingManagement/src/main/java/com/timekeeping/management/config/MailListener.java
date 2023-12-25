package com.timekeeping.management.config;

import com.timekeeping.management.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class MailListener {
    @Autowired
    MailServiceImpl mailService;

    /**
     * send mail using Async when send   much mail at the time
     * @param senderMailEvent
     * @throws MessagingException
     */
    @Async
    @EventListener
    public void  sendEmailEventListener (SenderMailEvent senderMailEvent) throws MessagingException {
        mailService.sendMail(senderMailEvent.getCusMail(),senderMailEvent.getMailTemplateName());
    }
}
