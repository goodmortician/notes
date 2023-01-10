package com.goodmortician.notes.auth;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@ToString
public class AuthHandlerInterceptor implements HandlerInterceptor {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthHandlerInterceptor(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //Проверяем что над методом есть аннотация
            if (handlerMethod.hasMethodAnnotation(Authenticate.class)) {
                try {
                    Authenticate authenticate = handlerMethod.getMethodAnnotation(Authenticate.class);
                    Set<String> allowedRoles = new HashSet<>(Arrays.asList(authenticate != null ? authenticate.allowedRoles() : new String[0]));
                    String username = getUserNameFromToken(request, response);
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                    if (!CollectionUtils.isEmpty(allowedRoles)) {
                        boolean match = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(allowedRoles::contains);
                        if (!match) {
                            respondError(response, HttpStatus.FORBIDDEN, "FORBIDDEN", "API not allowed fot this role");
                            return false;
                        }
                    }
                } catch (HttpServerErrorException e) {
                    respondError(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getStatusText(), e.getMessage());
                    return false;
                }
            }
//            else {
//                //Если аннотации нет, то либо даем доступ всем, даже без авторизации или не даем.
//                // Или проверяем просто наличие активного токена
//                // убрать ошибку и реализовать код самостоятельно, на свое усмотрение.
//                respondError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Error", "Authenticate not implemented");
//                return false;
//            }
        }
        return true;
    }

    private String getUserNameFromToken(HttpServletRequest request, HttpServletResponse response) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token");
                respondError(response, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "Token not present");

            } catch (ExpiredJwtException e) {
                log.error("JWT Token has expired");
                respondError(response, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "JWT Token has expired");
            }
        }
        return username;
    }

    private void respondError(HttpServletResponse response, HttpStatus status, String code, String errorMessage) {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("x-error-code", code);
        response.setHeader("x-error-msg", errorMessage);
    }
}
