package com.example.iotassistantrest.admin.config;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MobileTokenFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger log = LoggerFactory.getLogger(MobileTokenFilter.class);

    protected MobileTokenFilter() {
        super("/api/mobile/**");
        this.setAuthenticationManager(new MobileTokenAuthenticationManager());
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        boolean success = true;
        Authentication authResult = null;
        try {
            authResult = this.attemptAuthentication(httpRequest, httpResponse);
        } catch (InternalAuthenticationServiceException e) {
            log.error("Internal authentication failed");
            success = false;
        } catch (AuthenticationException ae) {
            success = false;
        }

        if(success && authResult != null) {
            log.info("Token verified!");
            this.successfulAuthentication(httpRequest, httpResponse, chain, authResult);
        }
        chain.doFilter(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("Successful Authentication for user ${authResult?.principal}");
        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(headerToken==null) {
            throw new BadCredentialsException("Bearer token required!");
        }
        final String auth = headerToken.split(" ")[1];
        log.info(auth);

        MobileToken mobileToken = new MobileToken(auth);

        return getAuthenticationManager().authenticate(new JwtAuthentication(mobileToken));
    }
}
