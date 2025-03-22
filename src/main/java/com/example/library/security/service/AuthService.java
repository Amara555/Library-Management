package com.example.library.security.service;

import com.example.library.security.model.CustomUserDetails;
import com.example.library.utils.constants.ResponseMessage;
import com.example.library.utils.model.HibernateAwareObjectMapper;
import com.example.library.utils.model.ResponseObject;
import com.example.library.utils.services.MessageSourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthService {
//    final ApplicationUserPrivilegeService applicationUserPrivilegeService;


//    @Autowired
//    SystemPrivilegeService systemPrivilegeService;

    @Autowired
    Environment environment;

    @Autowired
    protected MessageSourceService messageSourceService;


    public void buildLoginSuccessResponse(HttpServletRequest request, HttpServletResponse servletResponse,
                                          CustomUserDetails userDetails) {
//    Map<String, Object> extraData = new HashMap<>();
        HibernateAwareObjectMapper mapper = new HibernateAwareObjectMapper();
//        extraData.put("server_url", getServerURL());

//        Set<SystemPrivilege> systemPrivileges =
//                systemPrivilegeService.getAllSystemPrivilegeForUser(userDetails.getApplicationUser().getId());
        try {
//            if (userDetails.getApplicationUser().getRecordStatus().equals(RecordStatusEnum.Active.getStatus())) {
//                if (!systemPrivileges.isEmpty()) {
//                    userDetails.getApplicationUser().setSystemPrivileges(systemPrivileges);
//                }
//            }
            String response = mapper.writeValueAsString(ResponseObject.SUCCESS_RESPONSE(
                    ResponseMessage.User_LoggedIn_SUCCESS, userDetails.getApplicationUser(), null).getBody());
//            String response = mapper.writeValueAsString(ResponseObject.SUCCESS_RESPONSE("user.successfully.loggedIn",userDetails.getApplicationUser(), extraData));
            servletResponse.setContentType("application/json");
            servletResponse.getWriter().print(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildLoginFailedResponse(HttpServletRequest request, HttpServletResponse servletResponse,
                                         String msg) {
        HibernateAwareObjectMapper mapper = new HibernateAwareObjectMapper();
        try {
            String response = mapper.writeValueAsString(ResponseObject.FAILED_RESPONSE(msg, null, HttpStatus.valueOf(servletResponse.getStatus())));
            servletResponse.setContentType("application/json");
            servletResponse.getWriter().print(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private String getServerURL() {
//        Object bank = RequestContextHolder.getRequestAttributes().getAttribute("bank", 1);
//        if (bank != null && bank.toString().equals("SIIB"))
//            return environment.getProperty("SIIB_SERVER_URL");
//        else
//            return environment.getProperty("FATORA_SERVER_URL");
//
//    }
}
