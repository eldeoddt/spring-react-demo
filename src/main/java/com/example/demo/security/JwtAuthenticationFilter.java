package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // OncePerRequestFilter를 상속받아서 Filter를 구현한다.

    @Autowired
    private TokenProvider tokenProvider;

    /**
     * token 이 유효한지 검증하고 Security Context 객체를 생성합니다.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 token을 가져온다.
            String token = parseBearerToken(request);
            log.info("JwtAuthenticationFilter is running ...");

            // 토큰이 null이 아닌 경우 실행한다.
            if (token != null && !token.equalsIgnoreCase("null")) {
                // token이 유효한지 확인 -> token에서 userId를 추출한다.
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user Id : " + userId);

                // 현재 요청한 사용자를 SecurityContextHolder에 등록해야 한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        AuthorityUtils.NO_AUTHORITIES // 사용자 권한
                );
                // 어떤 요청에 대한 인증인지
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // contextholder에 빈 context를 얻어서 securityContext에 담는다.
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 요청에서 token을 추출합니다.
     * @param request
     * @return token
     */
    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
