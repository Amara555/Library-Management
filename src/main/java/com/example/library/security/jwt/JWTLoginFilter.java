package com.example.library.security.jwt;

import com.example.library.security.model.CustomUserDetails;
import com.example.library.security.model.LoginRequest;
import com.example.library.security.service.AuthService;
import com.example.library.security.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component("jwtLoginFilter")
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final static String LOGIN_URL = "/login";

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private AuthService authService;


    public JWTLoginFilter() {
        super(new AntPathRequestMatcher(LOGIN_URL));
        // set empty authentication manager
        setAuthenticationManager(new AuthenticationManager() {
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {

        // Retrieve email and password from the http request and save them in an Account object.

        String userName = "";
        String password = "";
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(req.getReader(), LoginRequest.class);
            userName = loginRequest.getUserName();
            password = loginRequest.getPassword();

            if (loginRequest.getUserName() == null) {
                loginRequest.setUserName(null);
            }

            if (loginRequest.getUserName() == null || loginRequest.getUserName().isEmpty()
                    || loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty() ) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                authService.buildLoginFailedResponse(req, res, "invalid.credentials");
                return null;
            }
            req.setAttribute("password", password);
        } catch (Exception e) {
            e.printStackTrace();
            authService.buildLoginFailedResponse(req, res, "user.login.failed");
            return null;
        }


        ServletRequestAttributes attributes = new ServletRequestAttributes(req, res);
        initContextHolders(req, attributes);
        // Verify if the correctness of login details.
        // If correct, the successfulAuthentication() method is executed.
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        password,
                        new ArrayList<GrantedAuthority>()
                )
        );
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                         Authentication auth) throws IOException, ServletException {

        // Pass authenticated user data to the tokenAuthenticationService in order to add a JWT to the http response.
        tokenAuthenticationService.addAuthentication(req, res, auth);
        authService.buildLoginSuccessResponse(req, res, (CustomUserDetails) auth.getPrincipal());
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        authService.buildLoginFailedResponse(request, response, "invalid.credentials");
    }

    private void initContextHolders(HttpServletRequest request, ServletRequestAttributes requestAttributes) {
        LocaleContextHolder.setLocale(request.getLocale());
        RequestContextHolder.setRequestAttributes(requestAttributes);
        if (logger.isTraceEnabled()) {
            logger.trace("Bound request context to thread: " + request);
        }
    }
}
