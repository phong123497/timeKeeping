package com.timekeeping.management.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timekeeping.common.auth.CustomUserDetails;
import com.timekeeping.common.auth.JwtTokenProvider;
import com.timekeeping.common.component.ResponseData;
import com.timekeeping.common.config.TimeKeepingCustomHttpServletRequest;
import com.timekeeping.common.constant.ApiWhiteListContextPathConst;
import com.timekeeping.common.constant.ApplicationConstant;
import com.timekeeping.common.constant.MessageCodeConst;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.dto.JwtAuthDto;
import com.timekeeping.common.exception.ApiError;
import com.timekeeping.common.exception.UnauthorizedException;
import com.timekeeping.common.util.StringUtil;
import com.timekeeping.management.service.impl.UserDetailsServiceImpl;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author minhtq2 on 23/10/2023
 * @project TimeKeeping
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RedissonClient redissonClient;

    @Value("${jwt.jwtTtlInMinutes}")
    private Integer jwtTtlInMinutes;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            TimeKeepingCustomHttpServletRequest scrwRequest = new TimeKeepingCustomHttpServletRequest(request);
            String servletContextPath = request.getServletPath();
            request.getSession().setAttribute(ApplicationConstant.AUTHENTICATION_SCHEME_NAME,
                    request.getHeader(ApplicationConstant.AUTHENTICATION_SCHEME_NAME));

            boolean hasRemoveToken = false;
            for (String whiteListItem : ApiWhiteListContextPathConst.authWhiteList) {
                if (new AntPathMatcher().match(whiteListItem, servletContextPath)) {
                    if (StringUtil.isNotEmpty(request.getHeader(ApplicationConstant.AUTHENTICATION_SCHEME_NAME))) {
                        scrwRequest.removeHeader(ApplicationConstant.AUTHENTICATION_SCHEME_NAME);
                        hasRemoveToken = true;
                        break;
                    }
                }
            }

            if (hasRemoveToken) {
                filterChain.doFilter(scrwRequest, response);
                return;
            } else {
                String auth = request.getHeader(ApplicationConstant.AUTHENTICATION_SCHEME_NAME);
                RMapCache<String, Object> rMapCacheToken = redissonClient
                        .getMapCache(ApplicationConstant.REDIS.NAME_GROUP_USER_TOKEN);

                if (auth != null && auth.startsWith(ApplicationConstant.TOKEN_SCHEME_NAME)) {
                    String jwt = tokenProvider.getJwtFromRequest(request);
                    String jwtCheck = null;
                    boolean hasJwtRedis = rMapCacheToken.get(jwt) != null;
                    //                boolean isLogout = !hasJwtRedis && request.getPathInfo().contains("/user/logout");
                    if (hasJwtRedis) {
                        jwtCheck = (String) rMapCacheToken.get(jwt);
                    }
                    JwtAuthDto jwtAuthDto;
                    if (StringUtils.hasText(jwtCheck) && tokenProvider.validateToken(jwtCheck)) {// && !isLogout
                        jwtAuthDto = tokenProvider.getJWTInfor(jwtCheck);
                        // UserDetails userDetails=
                        // CustomUserDetails.builder().user(User.builder().userId(jwtAuthDto.getUi()).username(jwtAuthDto.getUname()).)
                        CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService
                                .loadUserByUsername(jwtAuthDto.getUserName());
                        if (null != customUserDetails) {
                            customUserDetails.setSession(jwtAuthDto.getSs());
                            customUserDetails
                                    .setJwtTtlInMinutes(jwtTtlInMinutes);//ApplicationConstant.REDIS.jwtTtlInMinutes
                            // If authenticated, set data to Seturity Context
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    customUserDetails, customUserDetails.getPassword(),
                                    customUserDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);

                            RMapCache<String, Object> rMapCache = redissonClient
                                    .getMapCache(
                                            ApplicationConstant.REDIS.PREFIXES_GROUP_USER + jwtAuthDto.getUserName());

                            boolean hasTokenRedis = rMapCache
                                    .get(customUserDetails.getSession() + ApplicationConstant.REDIS.SUFFIXES_TOKEN)
                                    != null;
                            if (customUserDetails.getSession() != null) {
                                String token = tokenProvider.generateToken(customUserDetails);
                                rMapCacheToken
                                        .put(jwt, token, customUserDetails.getJwtTtlInMinutes(), TimeUnit.MINUTES);
                                rMapCache.put(customUserDetails.getSession() + ApplicationConstant.REDIS.SUFFIXES_TOKEN,
                                        jwt, customUserDetails.getJwtTtlInMinutes(), TimeUnit.MINUTES);
                            }

                            //                        UserSession userSession = userSessionRepository.findAllUserIdAndSession(jwtAuthDto.getUsername(), jwtAuthDto.getSs());
                            if (!hasTokenRedis) {
                                throw new UnauthorizedException(MessageCodeConst.LOGIN_EXPIRED);
                            }
                        }
                    } else {
                        throw new UnauthorizedException(MessageCodeConst.LOGIN_EXPIRED);
                    }
                }
            }
        } catch(Exception ex){
            SecurityContextHolder.clearContext();
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(
                    mapper.writeValueAsString(new ResponseData<Object>(ResponseStatusConst.ERROR,
                            new ApiError(MessageCodeConst.UNAUTHORIZED, ex.getMessage())))
            );
            return;
        }
        filterChain.doFilter(request, response);
    }

}
