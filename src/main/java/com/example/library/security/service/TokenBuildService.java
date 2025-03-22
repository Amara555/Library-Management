package com.example.library.security.service;

import com.example.library.security.model.CustomUserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenBuildService {

    public Map<String, Object> buildUserDetails(CustomUserDetails customUserDetails) {
        Map<String, Object> userDetails = new HashMap<String, Object>();
        userDetails.put("userName", customUserDetails.getApplicationUser().getUsername());
        userDetails.put("userId", customUserDetails.getApplicationUser().getId());
//        userDetails.put("mobile", customUserDetails.getMobile());
        return userDetails;
    }

}
