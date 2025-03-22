package com.example.library.Controller;

import com.example.library.Controller.Generic.GenericController;
import com.example.library.Dao.ApplicationUserDao;
import com.example.library.Dao.PatronDao;
import com.example.library.Services.ApplicationUserService;
import com.example.library.Services.PatronService;
import com.example.library.domain.ApplicationUser;
import com.example.library.domain.Book;
import com.example.library.domain.Patron;
import com.example.library.utils.annotation.ParameterName;
import com.example.library.utils.constants.ResponseMessage;
import com.example.library.utils.model.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patrons")
public class PatronController extends GenericController<PatronService, PatronDao, Patron,Integer> {


    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Object> save(@ParameterName("patron") Patron patron) throws Exception {
        Patron newPatron =  service.save(patron);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.ADDED_SUCCESS, newPatron, null);
    }
    @RequestMapping( path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@ParameterName("patron") Patron patron) throws Exception {
        Patron newPatron =  service.save(patron);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.ADDED_SUCCESS, newPatron, null);
    }

}
