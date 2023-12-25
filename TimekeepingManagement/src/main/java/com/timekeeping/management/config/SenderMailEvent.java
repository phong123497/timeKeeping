package com.timekeeping.management.config;

import com.timekeeping.common.entity.CusMail;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class SenderMailEvent  extends ApplicationEvent {

    private CusMail cusMail;
    private String mailTemplateName;

    /**
     * send mail  using template
     * @param source
     * @param cusMail
     * @param mailTemplateName
     */
    public SenderMailEvent(Object source,CusMail cusMail, String mailTemplateName) {
        super(source);
        this.cusMail= cusMail;
        this.mailTemplateName= mailTemplateName;
    }

}
