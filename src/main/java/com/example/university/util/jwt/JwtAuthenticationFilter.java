package com.example.university.util.jwt;

import com.example.university.model.entity.User;
import com.example.university.repository.UserRepository;
import com.example.university.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/api/auth/login") || path.contains("/media")) {
            filterChain.doFilter(request, response); // Skip this filter for certain paths
        } else {
            try {
                String jwt = getJwtFromRequest(request);

                User user = tokenProvider.validateToken(jwt);

                if (StringUtils.hasText(jwt) && user != null) {
//                    UserToken userToken = userTokenRepository.findByToken(jwt);
//
//                    if (userToken == null) {
//                        throw new ServiceException("You don't have permission to access");
//                    }

//                    User user = userRepository.findByUserIdAndIsActive(userToken.getUserId(), true).orElse(null);

                    if (user == null) {
                        throw new ServiceException("You don't have permission to access");
                    } else {
//                        user.setAccessToken(userToken.getToken());

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        user,
                                        null,
                                        null);

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception ex) {
                log.error("failed on set user authentication", ex);
            }

            filterChain.doFilter(request, response);
        }
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        String token = request.getParameter("Bearer");
        if (StringUtils.hasText(token)) {
            return token;
        }
        String jwt = getJwtFromCookies(request);
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }

    public static String getJwtFromCookies(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Bearer")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}