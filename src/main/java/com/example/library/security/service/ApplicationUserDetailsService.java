package com.example.library.security.service;

import com.example.library.Dao.ApplicationUserDao;
import com.example.library.Services.RoleService;
import com.example.library.domain.*;
import com.example.library.domain.ApplicationUser;
import com.example.library.security.model.CustomUserDetails;
import com.example.library.utils.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationUserDao applicationUserDao;
    @Autowired
    private RoleService roleService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Fetch the appUser corresponding to the given username
        ApplicationUser appUser = applicationUserDao.findByUserName(username);


        // If the appUser doesn'.txt exist
        if (appUser == null) {
            throw new UsernameNotFoundException("Database User " + username + " not found");
        }

        // If the LDAP User doesn'.txt exist
        String password = appUser.getPassword();


        Set<GrantedAuthority> authorities = new HashSet<>();
    if (appUser.getRole()==null){
        Role role = roleService.FindRoleByRoleName("Admin");
        appUser.setRole(role);
    }
        if (appUser.getRole().getName()!=null)
        authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName()));
        else     authorities.add(new SimpleGrantedAuthority(RoleType.User.name()));



        User user = new CustomUserDetails(appUser.getUsername(), (String) password,
                true, true, true,
                true,authorities);

        ((CustomUserDetails) user).setApplicationUser(appUser);


        return user;
    }


}
