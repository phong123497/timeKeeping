package com.timekeeping.management.controller;

import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.dto.UsersDTO;
import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.exception.ApiError;
import com.timekeeping.common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author minhtq2 on 23/10/2023
 * @project TimeKeeping
 */
@RestController
@RequestMapping("/users2")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get user info
     *
     * @return ResponseEntity<UserEntity
     */
    @GetMapping("/userAll")
    public ResponseEntity<?> getUser() {
        log.info("=== Start call api get users/user ===");
        ResponseEntity<?> response = null;
        try {
            Pageable pageable = PageRequest.of(0, 10);
            Page<UserEntity> userEntitys = userRepository.findAll(pageable);
            response = ResponseDataConfiguration.success(new UsersDTO(userEntitys));
        } catch (Exception ex) {
            ApiError error = new ApiError();
            error.setMessage(ex.getMessage());
            response = ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("=== Exception api get users/user ===", ex.toString());
        }
        log.info("=== End call api get users/user ===");
        return response;
    }
}
