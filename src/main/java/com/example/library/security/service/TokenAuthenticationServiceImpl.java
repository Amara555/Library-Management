package com.example.library.security.service;

import com.example.library.security.jwt.TokenHandler;
import com.example.library.security.model.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    @Autowired
    private TokenHandler tokenHandler;



    /**
     * When a user successfully logs into the application, create a token for that user.
     *
     * @param res An http response that will be filled with an 'Authentication' header containing the token.
     */
    public void addAuthentication(HttpServletRequest request, HttpServletResponse res, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String JWT = tokenHandler.build(user);
        res.addHeader(tokenHandler.HEADER_STRING, tokenHandler.TOKEN_PREFIX + " " + JWT);
    }
    /**
     * The JWTAuthenticationFilter calls this method to verify the user authentication.
     * If the token is not valid, the authentication fails and the request will be refused.
     *
     * @param request An http request that will be check for authentication token to verify.
     * @return
     */
    public Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(tokenHandler.HEADER_STRING);

        if (token != null && token.startsWith(tokenHandler.TOKEN_PREFIX)) {
            // Parse the token.
            CustomUserDetails user = null;

            try {
                user = tokenHandler.parse(request, token);
            } catch (ExpiredJwtException e) {
//                e.printStackTrace();
                System.err.println("ExpiredJwtException : " +e.getMessage());
            } catch (UnsupportedJwtException e) {
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                e.printStackTrace();
            } catch (SignatureException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
            } else {
                return null;
            }

        }

        return null;

    }


}
