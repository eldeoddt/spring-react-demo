package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "sercretkey";

    /**
     * jwt 를 생성합니다.
     * @param userEntity
     * @return token
     */
    public String create(UserEntity userEntity) {
        Date expiryDate = Date.from(
                Instant.now() // 현재시각
                        .plus(1, ChronoUnit.DAYS)); // 현재시각+1일 후

        // 토큰을 리턴한다.
        return Jwts.builder() // jwts 라이브러리 사용
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 헤더, 서명을 위한 secretkey
                .setSubject(userEntity.getId()) // sub 고유 id
                .setIssuer("demo app") // iss 발행자
                .setIssuedAt(new Date()) // iat 시간
                .setExpiration(expiryDate) // exp 유효기간
                .compact();
    }

    /**
     * jwt를 검증합니다.
     * @param token
     * @return userEntity의 Id(sub)
     */
    public String validateAndGetUserId(String token) {
        // 토큰의 서명이 유효한지 확인한다.
            // .parseClaimsJws : 헤더와 페이로드를 .setSigningKey(secret_key)로 받은 secret key로 서명한다.
            //                   해당 값을 token의 서명과 비교하여 일치하면 claims 를 반환한다.
            // .getbody : 반환된 claims의 body만 가져온다.
        // iss, exp 등을 claim이라 하고 claims는 이것들의 집합이다.
        // parser로 parse
        Claims claims = Jwts.parser() // parser를 생성한다.
                .setSigningKey(SECRET_KEY) // secret key 전달
                .parseClaimsJws(token) //서명이 일치하는지 확인 후
                .getBody(); // payload의 body를 추출한다.

        return claims.getSubject(); // sub을 반환한다.
    }
}
