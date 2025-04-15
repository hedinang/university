package com.example.university.util.jwt;

import com.example.university.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwtToken}")
    private String JWT_SECRET;

    private final long JWT_EXPIRATION = 604800000L;

//    private final UserTokenRepository userTokenRepository;

//    public JwtTokenProvider(UserTokenRepository userTokenRepository) {
//        this.userTokenRepository = userTokenRepository;
//    }

    public String generateRefreshToken(User userDetails, String deviceId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        Map<String, Object> info = new HashMap<>();
        info.put("deviceId", deviceId);
        info.put("user", userDetails);


        return Jwts.builder()
                .setSubject(userDetails.getUserId())
//                .claim("deviceId", deviceId)
//                .claim("aa", "aaa")
                .setClaims(new HashMap<>())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String generateAccessToken(User userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        Map<String, Object> info = new HashMap<>();
        info.put("user", userDetails);
        return Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setClaims(info)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Map<String, Object> decodePayload(String token) {
        try {
            String[] parts = token.split("\\."); // Chia token thành 3 phần
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid JWT format");
            }

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payload, Map.class);
        } catch (Exception e) {
            log.error("Error decoding JWT payload", e);
        }
        return null;
    }


    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            log.error("Lỗi khi kiểm tra token: {}", e.getMessage());
            return true;
        }
    }

    public User validateToken(String authToken) {
        try {
            Jws<Claims> o = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(o.getBody().get("user"), User.class);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            Map<String, Object> claims = decodePayload(authToken);

            String userId = null;

            if (claims.get("sub") != null && claims.get("sub") instanceof String) {
                userId = (String) claims.get("sub");
            }

//            if (userId != null) {
//                List<UserToken> userTokens = userTokenRepository.findByUserId(userId);
//
//                if (!userTokens.isEmpty()) {
//                    List<String> expiredTokens = new ArrayList<>();
//
//                    for (UserToken userToken : userTokens) {
//                        if (userToken.getToken() != null) {
//                            boolean isExpired = isTokenExpired(userToken.getToken());
//
//                            if (isExpired) {
//                                expiredTokens.add(userToken.getToken());
//                            }
//                        }
//                    }
//
//                    try {
//                        if (!expiredTokens.isEmpty()) {
//                            userTokenRepository.deleteByTokenIn(expiredTokens);
//                        }
//                    } catch (Exception e) {
//                        log.error("Delete token expired device failed: " + e);
//                    }
//                }
//            }
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return null;
    }
}