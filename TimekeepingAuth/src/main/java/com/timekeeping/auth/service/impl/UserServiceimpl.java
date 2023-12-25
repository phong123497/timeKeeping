package com.timekeeping.auth.service.impl;

import com.timekeeping.auth.service.UserService;
import com.timekeeping.auth.util.TimekeepingAuthenUtil;
import com.timekeeping.common.auth.CustomUserDetails;
import com.timekeeping.common.auth.JwtAuthenticationResponse;
import com.timekeeping.common.auth.JwtTokenProvider;
import com.timekeeping.common.constant.ApplicationConstant;
import com.timekeeping.common.constant.MessageCodeConst;
import com.timekeeping.common.dto.UserDto;
import com.timekeeping.common.dto.UserLoginDto;
import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.exception.CommonException;
import com.timekeeping.common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.concurrent.TimeUnit;

/**
 * @author minhtq2 on 18/10/2023
 * @project TimeKeeping
 */
@Service
@Slf4j
public class UserServiceimpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${jwt.jwtTtlInMinutes}")
    private Integer jwtTtlInMinutes;

    @Override
    public JwtAuthenticationResponse login(UserLoginDto userLoginDto) throws Exception {
        Authentication authentication;
        UserEntity user;
        RMapCache<String, Object> rMapCache;
        RMapCache<String, Object> rMapCacheLock;
        RMapCache<String, Object> rMapCacheToken;

        long countLock = 0;
        try {
            rMapCacheToken = redissonClient.getMapCache(ApplicationConstant.REDIS.NAME_GROUP_USER_TOKEN);
            rMapCacheLock = redissonClient.getMapCache(ApplicationConstant.REDIS.LOCK_USER);

            if (userLoginDto.getPassword().isEmpty()) {
                throw new CommonException().setErrorCode(MessageCodeConst.LOGIN_INFO_INVALID).addPlaceholder("password")
                        .setStatusCode(HttpStatus.BAD_REQUEST).setField("password");
            }

            // (1) Get user info
            user = userRepository.getUserEntityByUsername(userLoginDto.getUsername());

            // check (1) have data?
            if (user == null) {
                rMapCacheLock.put(ApplicationConstant.REDIS.LOCK_USER + "_" + userLoginDto.getUsername(),
                        countLock + 1, 5, TimeUnit.MINUTES);
                throw new CommonException().setErrorCode(MessageCodeConst.LOGIN_INFO_INVALID)
                        .setStatusCode(HttpStatus.BAD_REQUEST);
            }


            // Get user authentication from redis
            rMapCache = redissonClient
                    .getMapCache(ApplicationConstant.REDIS.PREFIXES_GROUP_USER + user.getUsername());
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), userLoginDto.getPassword()));

            // authentication user
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            String session = servletRequestAttributes.getSessionId();

            // Get user detail
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            customUserDetails.setSession(session);
            customUserDetails.setJwtTtlInMinutes(jwtTtlInMinutes);// ApplicationConstant.REDIS.jwtTtlInMinutes

            log.info("login - " + TimekeepingAuthenUtil.getAPI() + " - " + userLoginDto.getUsername());

            String token = tokenProvider.generateToken(customUserDetails);
            rMapCacheToken.put(token, token, customUserDetails.getJwtTtlInMinutes(), TimeUnit.MINUTES);
            rMapCache.put(customUserDetails.getSession() + ApplicationConstant.REDIS.SUFFIXES_TOKEN, token,
                    customUserDetails.getJwtTtlInMinutes(), TimeUnit.MINUTES);


            return JwtAuthenticationResponse.builder().accessToken(token)
                    .user(getUserDtoByUser(user)).build();
        } catch (CommonException e) {
            log.error("==== API login error: " + ExceptionUtils.getStackTrace(e));
            throw e;
        } catch (Exception e) {
            log.error("==== API login error: " + ExceptionUtils.getStackTrace(e));
            throw new CommonException().setErrorCode(MessageCodeConst.FAILED)
                    .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private UserDto getUserDtoByUser(UserEntity user) {
        return new UserDto(user.getEmployeeId(), user.getUsername(), user.getRole());
    }
}
