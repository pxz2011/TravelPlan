//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxzq.travel_plan.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pxzq.travel_plan.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;


@Component
@Slf4j
public class JwtUtil {
    private static final String TOKENKEY = "cnm sb nmsl";
    static User user = new User();
    static
    RedisUtil redisUtil;

    public static String getToken(String userName, String Password) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        String Token = JWT.create().withClaim("userName", userName).withClaim("password", Password).withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256("i'amtoken!xlhtk!@#$%^&*()"));
        log.info("token:{}", Token);
        return Token;
    }

    public static boolean verify(String token) {
        System.out.println("验证token" + JWT.require(Algorithm.HMAC256(TOKENKEY)).build().verify(token));
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKENKEY)).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
            log.info("userName=" + jwt.getClaim("userName").asString());
            String redisToken = (String) redisUtil.get("token_" + jwt.getClaim("userName").asString());
            log.info("redisToken=" + redisToken);
            System.out.println(redisToken.equals(token));
            return redisToken.equals(token);
        } catch (Exception e) {
            return false;
        }

    }

    public static User parse(String token) throws Exception {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKENKEY)).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            String userName = verify.getClaim("userName").asString();
            String password = verify.getClaim("password").asString();
            System.out.println("userName = " + userName);
            System.out.println("password = " + password);
            if (userName == null || password == null) {
                return null;
            }

            user.setUserName(userName);
            user.setPassword(password);
        } catch (TokenExpiredException var5) {
            throw new Exception("token已失效!!,请重新登录!!", var5);
        } catch (JWTDecodeException | SignatureVerificationException var6) {
            throw new Exception("token错误!", var6);
        }

        return user;
    }
}
