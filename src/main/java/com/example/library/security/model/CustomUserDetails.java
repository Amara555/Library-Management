package com.example.library.security.model;

import com.example.library.domain.ApplicationUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails extends User {

    private String userName;
    private String password;
    private ApplicationUser applicationUser;

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }


    public CustomUserDetails(ApplicationUser applicationUser, String password) {
        super(applicationUser.getUsername(), password,
                true, true, true,
                true, new ArrayList<GrantedAuthority>());
        this.applicationUser = applicationUser;
        this.userName = applicationUser.getUsername();
        this.password = applicationUser.getPassword();

    }

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userName = username;
        this.password = password;
    }

    public CustomUserDetails(String userName, String password, boolean enabled
            , boolean accountNonExpired, boolean credentialsNonExpired
            , boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(userName, password, true, true, true, true, authorities);
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.userName = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static CustomUserDetails getCurrentInstance() {
        if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null)
            return null;
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserDetails))
            return null;
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails;
    }


}
