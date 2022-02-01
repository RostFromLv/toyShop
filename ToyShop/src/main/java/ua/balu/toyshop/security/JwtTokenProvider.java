package ua.balu.toyshop.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final UserDetailServiceImpl userDetailService;



//    @Value("${token.time}")
    private final long TOKEN_DAYS_LIFE_TIME = 1;
//    @Value("$(jwt.secret)")
    private  String secretJwt = "Java-smell";
//    @Value("$(header)")
    private final String header="Authorization";

    @Autowired
    public JwtTokenProvider(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostConstruct
    protected void init() {
        secretJwt = Base64.getEncoder().encodeToString(secretJwt.getBytes());
    }

    public String createToken(String email, String role) {
        log.info("Creating token");
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("ROLE", role);

        Date expiredDateTime = Date.from(LocalDate.now()
                .plusDays(TOKEN_DAYS_LIFE_TIME)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiredDateTime)
                .signWith(SignatureAlgorithm.HS512, secretJwt)
                .compact();


    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretJwt).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.info("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.info("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.info("Malformed jwt");
        } catch (SignatureException sEx) {
            log.info("Invalid signature");
        } catch (Exception e) {
            log.info("invalid token");
        }
        return true;
    }

    public Authentication getAuthenticationFromToken(String token) {
        log.info("Getting  authentication from token");
        UserDetails userDetails = this.userDetailService.loadUserByUsername(getEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmailFromToken(String token) {
        log.info("Get email from token");
        Claims claims = Jwts.parser()
                .setSigningKey(secretJwt)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        log.info("Get jwt from Request");
        String bearerToken = request.getHeader(header);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
