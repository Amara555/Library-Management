package com.example.library.security.jwt;

import com.example.library.security.model.CustomUserDetails;
import com.example.library.security.service.ApplicationUserDetailsService;
import com.example.library.security.service.TokenBuildService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
@PropertySource({"classpath:generalProps/tokenHandler.properties"})
public class TokenHandler {


    //  hours * minutes * seconds * millies
    final long EXPIRATIONTIME = 2 * 60 * 60 * 1000;

    String SECRET="KACxazQ4n6VkL5B";

    @Value("${TOKEN_PREFIX}")
    public String TOKEN_PREFIX;          // the prefix of the token in the http header

    @Value("${HEADER_STRING}")
    public String HEADER_STRING;   // the http header containing the prefix + the token

    @Autowired
    private ApplicationUserDetailsService userDetailsService;



    @Autowired
    private TokenBuildService tokenBuildService;

    /**
     * Generate a token from the username.
     *
     * @param customUserDetails The subject from which generate the token.
     * @return The generated token.
     */
    public String build( CustomUserDetails customUserDetails) {

        String JWT = "";
        String SECRET = this.SECRET;
        Map<String, Object> userDetails = new HashMap<String, Object>();
        userDetails = tokenBuildService.buildUserDetails(customUserDetails);

        JWT = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(customUserDetails.getApplicationUser().getUsername())
                .setClaims(userDetails)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return JWT;

    }


    /**
     * Parse a token and extract the subject (username).
     *
     * @param token A token to parse.
     * @return The subject (username) of the token.
     */
    public CustomUserDetails parse(HttpServletRequest request, String token) {
        DefaultClaims tokenBody = (DefaultClaims) Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();

        String userName = (String) tokenBody.get("userName");
        request.setAttribute("userId", tokenBody.get("Id"));
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userName);
//        userDetails.setSystemModuleId((Integer) tokenBody.get("systemModuleId"));

        return userDetails;

    }


}
