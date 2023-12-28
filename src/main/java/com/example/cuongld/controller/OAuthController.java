package com.example.cuongld.controller;

import com.example.cuongld.config.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class OAuthController {
    private final JwtService jwtService;
    @GetMapping("/oauth2/callback/google")
    public ResponseEntity<Void> callback(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response) throws UnsupportedEncodingException, URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        System.out.println(oAuth2User.getAttributes());
        String email = oAuth2User.getAttributes().get("email").toString();
        String accessToken = jwtService.generateToken(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new OAuth2UserAuthority("USER", Map.of("email", email)));
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return email;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        });
        // Đặt mã JWT trong cookie hoặc header của response
        headers.add("Set-Cookie", "access_token=" + accessToken + "; Path=/; HttpOnly");
        response.addCookie(new Cookie("access_token", accessToken));
        headers.setLocation(URI.create("http://localhost:8080/slide"));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
