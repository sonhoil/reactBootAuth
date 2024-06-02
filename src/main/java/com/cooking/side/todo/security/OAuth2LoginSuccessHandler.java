package com.cooking.side.todo.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cooking.side.todo.model.User;
import com.cooking.side.todo.service.UserService;
import com.cooking.side.todo.util.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Value("${frontend.base-url}") // 프론트엔드 URL을 properties에서 읽어옵니다.
    private String frontendBaseUrl;

    public OAuth2LoginSuccessHandler(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String providerId = (String) oAuth2User.getAttributes().get("id").toString();

        Optional<User> userOptional = userService.findByProviderAndProviderId("kakao", providerId);
        if (userOptional.isPresent()) {
            String jwt = jwtUtils.generateJwtToken(authentication);
            response.setHeader("Authorization", "Bearer " + jwt);
            response.sendRedirect(frontendBaseUrl + "/success-url");  // 로그인 성공 후 리디렉션할 URL
        } else {
            // 이메일 정보를 회원가입 페이지로 전달
            response.sendRedirect(frontendBaseUrl + "/register?email=" + email + "&providerId=" + providerId);
        }
    }
}