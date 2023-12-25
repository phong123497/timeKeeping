package com.timekeeping.common.auth;

import com.timekeeping.common.constant.ApplicationConstant;
import com.timekeeping.common.dto.JwtAuthDto;
import com.timekeeping.common.dto.UserDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author minhtq2 on 10/10/2023
 * @project timekeeping-common
 */
@Component
@Slf4j
@Primary
public class JwtTokenProvider {
    private static final String EXPIRATION = "exp";
    private static final String SESSION = "ss";
    private static final String USER_NAME = "userName";

    private static final String ROLE = "role";

    @Value("${jwt.iss}")
    private String jwtIss;

    @Value("${jwt.ttlInDays}")
    private int jwtTtlInDays;

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private Key signingKey;

    public String generateToken(CustomUserDetails userDetails) {

        LocalDateTime currentTime = LocalDateTime.now();
        int jwtTtl =  jwtTtlInDays * 24 * 60;
        final Date expiryDate = Date.from(currentTime.plusMinutes(jwtTtl).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().setIssuer(jwtIss)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(expiryDate)
                .claim(USER_NAME, userDetails.getUserEntity().getUsername())
                .claim(SESSION, userDetails.getSession())
                .claim(ROLE, userDetails.getUserEntity().getRole())
                .signWith(SignatureAlgorithm.HS256, getSigningKey()).compact();
    }

    public JwtAuthDto getJWTInfor(String authToken) {

        Claims body = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(authToken).getBody();

        return JwtAuthDto.builder().issuer(body.getIssuer())
                .expi(new Timestamp(body.get(EXPIRATION, Date.class).getTime()))
                .userName(body.get(USER_NAME, String.class))
                .role(body.get(ROLE, String.class))
                .ss(body.get(SESSION, String.class)).build();
    }

    public boolean validateToken(String authToken) throws Exception {

        try {
            Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired and must be rejected: " + e.getMessage());
            throw new Exception("Expired and must be rejected: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt:" + e.getMessage());
            throw new Exception("Unsupported Jwt: " + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Malformed Jwt : " + e.getMessage());
            throw new Exception("Malformed Jwt : " + e.getMessage());
        } catch (SignatureException e) {
            log.error("Signature Exception: " + e.getMessage());
            throw new Exception("Signature Exception: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Illegal Argument: " + e.getMessage());
            throw new Exception("Illegal Argument: " + e.getMessage());
        }
    }

//    public boolean validateTokenBasic(String authToken) throws UnauthorizedException {
//        try {
//            String usernameAndPassword = EncryptUtils
//                    .encryptBase64(new StringBuilder(usernameDefault).append(":").append(passwordDefault).toString());
//            if (!usernameAndPassword.equals(authToken)) {
//                throw new UnsupportedJwtException("JwtException Token Basic.");
//            }
//            return true;
//        } catch (UnsupportedJwtException e) {
//            log.error(" JwtException: " + e.getMessage());
//            throw new UnauthorizedException("JwtException Token Basic.");
//        }
//    }

    /**
     * Create and get signing key.
     *
     * @return key for signing and verify jwt
     */
    private Key getSigningKey() {

        if (signingKey == null) {
            // Create key for sign the jwt
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
            signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        }
        return signingKey;
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(ApplicationConstant.AUTHENTICATION_SCHEME_NAME);
        return getJwtFromHeader(bearerToken);
    }

    public String getJwtFromHeader(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(ApplicationConstant.TOKEN_SCHEME_NAME)) {
            return bearerToken.substring(ApplicationConstant.TOKEN_SCHEME_NAME.length(), bearerToken.length());
        } else if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(ApplicationConstant.TOKEN_BASIC)) {
            return bearerToken.substring(ApplicationConstant.TOKEN_BASIC.length(), bearerToken.length());
        }
        return org.apache.commons.lang3.StringUtils.EMPTY;
    }
}
