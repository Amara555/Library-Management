package com.example.library.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface TokenAuthenticationService {

	/**
	 * When a user successfully logs into the application, create a token for that user.
	 * 
	 * @param res		An http response that will be filled with an 'Authentication' header containing the token.
	 */
	void addAuthentication(HttpServletRequest request, HttpServletResponse res, Authentication authentication) throws IOException, ServletException;

	/**
	 * The JWTAuthenticationFilter calls this method to verify the user authentication.
	 * If the token is not valid, the authentication fails and the request will be refused.
	 * 
	 * @param request	An http request that will be check for authentication token to verify.
	 * @return
	 */
	Authentication getAuthentication(HttpServletRequest request);

}
