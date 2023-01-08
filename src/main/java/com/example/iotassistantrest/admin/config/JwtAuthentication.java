package com.example.iotassistantrest.admin.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends AbstractAuthenticationToken {
    private final MobileToken mobileToken;

    public JwtAuthentication(MobileToken mobileToken) {
        super(mobileToken.getAuthorities());
        this.mobileToken = mobileToken;
    }

    public MobileToken getMobileToken() {
        return mobileToken;
    }

    @Override
    public Object getCredentials() {
        return mobileToken.getValue();
    }

    @Override
    public Object getPrincipal() {
        return mobileToken.getUsername();
    }
}
