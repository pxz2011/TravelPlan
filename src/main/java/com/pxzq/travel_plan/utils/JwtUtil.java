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
import com.pxzq.travel_plan.service.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Slf4j
@Component
/*
  jwt 工具包
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
        return JWT.create().
                withClaim("userName", userName).
                withClaim("password", Password).
                withClaim("userId", userId).
                withExpiresAt(calendar.getTime()).
                sign(Algorithm.HMAC256(TOKENKEY));
    }

    public static User parse(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKENKEY)).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            String userName = verify.getClaim("userName").asString();
            String password = verify.getClaim("password").asString();
            Long id = verify.getClaim("userId").asLong();
            if (userName == null || password == null) {
                return null;
            }
            user.setUserName(userName);
            user.setPassword(password);
            user.setId(id);
        } catch (TokenExpiredException var5) {
            throw new TokenException("令牌已失效");
        } catch (JWTDecodeException | SignatureVerificationException var6) {
            throw new RuntimeException("令牌错误!");
        }

        return user;
    }

    public boolean verify(String token) {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKENKEY)).build();
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
            String redisToken = String.valueOf(redisUtil.get(jwt.getClaim("userName").asString()));
            return redisToken.equals(token);
        } catch (TokenExpiredException expiredException) {
            throw new TokenException("令牌已失效");
        } catch (JWTDecodeException | SignatureVerificationException exception) {
            throw new TokenException("令牌错误");
        }

    }
}
