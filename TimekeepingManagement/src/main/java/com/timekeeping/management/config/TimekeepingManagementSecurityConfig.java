package com.timekeeping.management.config;

import com.timekeeping.common.auth.JwtTokenProvider;
import com.timekeeping.common.constant.ApiWhiteListContextPathConst;
import com.timekeeping.management.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * @author minhtq2 on 20/10/2023
 * @project TimeKeeping
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TimekeepingManagementSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("${readTimeout}")
    private Integer readTimeout;

    @Value("${connectionTimeout}")
    private Integer connectionTimeout;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers(ApiWhiteListContextPathConst.authWhiteListArr).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.httpFirewall(allowUrlEncodedHttpFirewall());
        web.ignoring().antMatchers(ApiWhiteListContextPathConst.authWhiteListArr);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public HttpFirewall allowUrlEncodedHttpFirewall() {
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        firewall.setAllowUrlEncodedSlash(true);
//        firewall.setAllowUrlEncodedDoubleSlash(true);
//        firewall.setAllowUrlEncodedPercent(true);
//        firewall.setAllowBackSlash(true);
//        firewall.setAllowSemicolon(true);
//        firewall.setAllowUrlEncodedPeriod(true);
//        return firewall;
//    }

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        UrlPathHelper urlPathHelper = new UrlPathHelper();
//        urlPathHelper.setUrlDecode(false);
//        configurer.setUrlPathHelper(urlPathHelper);
//    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() { return new JwtTokenProvider();}

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate
                .getRequestFactory();
        //        System.out.println("======== Setting timeout: readTimeout = " + readTimeout + "\tconexTimeout = " + connectionTimeout);
        requestFactory.setReadTimeout(readTimeout * 10000);
        requestFactory.setConnectTimeout(connectionTimeout * 10000);
        return restTemplate;
    }
}

