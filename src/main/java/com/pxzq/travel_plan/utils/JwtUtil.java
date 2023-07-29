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

@Slf4j
@Component
/**
 * jwt 工具包
 */
public class JwtUtil {
    private static final String TOKENKEY = "cnm sb nmsl,傻逼你妈死了 彭峰摸得卵子";
    static User user = new User();
    final
    RedisUtil redisUtil;

    public JwtUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public static String getToken(String userName, String Password, Long userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        String Token = JWT.create().
                withClaim("userName", userName).
                withClaim("password", Password).
                withClaim("userId", userId).
                withExpiresAt(calendar.getTime()).
                sign(Algorithm.HMAC256(TOKENKEY));
        log.info("token:{}", Token);
        log.info("过期时间:{}", calendar.getTime());
        return Token;
    }

    public static User parse(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKENKEY)).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            String userName = verify.getClaim("userName").asString();
            String password = verify.getClaim("password").asString();
            Long id = verify.getClaim("userId").asLong();
            log.info("userName = " + userName);
            log.info("password = " + password);
            log.info("userID = " + id);
            if (userName == null || password == null) {
                return null;
            }

            user.setUserName(userName);
            user.setPassword(password);
            user.setId(id);
        } catch (TokenExpiredException var5) {
            throw new RuntimeException("token已失效!!,请重新登录!!", var5);
        } catch (JWTDecodeException | SignatureVerificationException var6) {
            throw new RuntimeException("token错误!", var6);
        }

        return user;
    }

    public boolean verify(String token) {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKENKEY)).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
            log.info("userName=" + jwt.getClaim("userName").asString());
            String redisToken = String.valueOf(redisUtil.get(jwt.getClaim("userName").asString()));
            log.info("redisToken=" + redisToken);
            log.info("token = {}", token);
            return redisToken.equals(token);
        } catch (TokenExpiredException expiredException) {
            log.error("token过期");
            throw new RuntimeException("token过期!!!");
        } catch (JWTDecodeException | SignatureVerificationException exception) {
            log.error("token错误");
            throw new RuntimeException("token错误!!!");
        }

    }
}
