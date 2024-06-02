package com.cooking.side.todo.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequestEntityConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CustomRequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter {
    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        RequestEntity<?> entity = super.convert(authorizationCodeGrantRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(entity.getHeaders());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(OAuth2ParameterNames.GRANT_TYPE, authorizationCodeGrantRequest.getGrantType().getValue());
        body.add(OAuth2ParameterNames.CLIENT_ID, authorizationCodeGrantRequest.getClientRegistration().getClientId());
        body.add(OAuth2ParameterNames.CLIENT_SECRET, authorizationCodeGrantRequest.getClientRegistration().getClientSecret());
        body.add(OAuth2ParameterNames.CODE, authorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode());
        body.add(OAuth2ParameterNames.REDIRECT_URI, authorizationCodeGrantRequest.getAuthorizationExchange().getAuthorizationRequest().getRedirectUri());

        return new RequestEntity<>(body, headers, entity.getMethod(), entity.getUrl());
    }
}