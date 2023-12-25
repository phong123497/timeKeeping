package com.timekeeping.auth.controller;

import com.timekeeping.auth.service.UserService;
import com.timekeeping.common.auth.JwtAuthenticationResponse;
import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.dto.UserLoginDto;
import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.exception.ApiError;
import com.timekeeping.common.exception.CommonException;
import com.timekeeping.common.repository.UserRepository;
import com.timekeeping.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author minhtq2 on 16/10/2023
 * @project BE
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get user info
     *
     * @return ResponseEntity<UserEntity
     */
    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUser() {
        log.info("=== Start call api get users/user ===");
        ResponseEntity<UserEntity> response = null;
        try {
            UserEntity userEntity = userRepository.getUserEntityByEmployeeId("abc");
            response = ResponseDataConfiguration.success(userEntity);
//            String pass = passwordEncoder.encode(userEntity.getPassword());
//            log.info("=== password ===", userEntity.getPassword());
//        }
//        catch (CommonException ce) {
//            log.info("=== Exception api get users/user ===", ce.toString());
        } catch (Exception ex) {
            ApiError error = new ApiError();
            error.setMessage(ex.getMessage());
            response = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("=== Exception api get users/user ===", ex.toString());
        }
        log.info("=== End call api get users/user ===");
        return response;
    }

    @PostMapping("/authorization")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody UserLoginDto userLoginDto) {
        ResponseEntity<JwtAuthenticationResponse> response;
        try {
            log.info("===================== call login API: " + DateUtils.getDateCurrent());
            response = ResponseDataConfiguration.success(userService.login(userLoginDto));
        } catch (CommonException ex) {
            ApiError error = new ApiError(ex.getErrorCode(), ex.getMessage());
            response = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, ex.getStatusCode());
        } catch (Exception ex) {
            ApiError error = new ApiError();
            error.setMessage(ex.getMessage());
            response = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("===================== end login API: " + DateUtils.getDateCurrent());
        return response;
    }

}
