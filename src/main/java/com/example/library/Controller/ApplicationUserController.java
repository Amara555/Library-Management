package com.example.library.Controller;

import com.example.library.Controller.Generic.GenericController;
import com.example.library.Dao.ApplicationUserDao;
import com.example.library.Services.ApplicationUserService;
import com.example.library.domain.ApplicationUser;
import com.example.library.utils.annotation.ParameterName;
import com.example.library.utils.constants.ResponseMessage;
import com.example.library.utils.model.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ApplicationUserController extends GenericController<ApplicationUserService, ApplicationUserDao, ApplicationUser,Integer> {


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> save(@ParameterName("user") ApplicationUser user) throws Exception {
     ApplicationUser nwwUser =  service.save(user);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.ADDED_SUCCESS, nwwUser, null);
    }

}
