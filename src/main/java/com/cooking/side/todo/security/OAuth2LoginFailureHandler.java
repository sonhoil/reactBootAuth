package com.cooking.side.todo.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        exception.printStackTrace(); // 에러를 로그로 출력
        String targetUrl = "/login?error=true&message=" + exception.getLocalizedMessage();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
