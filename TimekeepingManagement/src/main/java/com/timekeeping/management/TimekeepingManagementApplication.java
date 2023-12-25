package com.timekeeping.management;

import com.timekeeping.common.config.CorsFilter;
import com.timekeeping.common.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CorsFilter.class, SwaggerConfiguration.class})
public class TimekeepingManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimekeepingManagementApplication.class, args);
    }

}
