package com.example.library.security.jwt;

import com.example.library.security.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("jwtAuthenticationFilter")
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) httpServletRequest);

        // Apply the authentication to the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Go on processing the request
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        // Clears the context from authentication
        SecurityContextHolder.clearContext();
    }
}
