package com.example.library.Controller.Generic;

import com.example.library.Dao.GenericDao;
import com.example.library.Services.Generic.GenericService;
import com.example.library.domain.generic.GenericDomain;
import com.example.library.exeption.SpecificExceptions.ResourceNotFoundException;
import com.example.library.utils.annotation.ParameterName;
import com.example.library.utils.constants.ResponseMessage;
import com.example.library.utils.model.Pagination;
import com.example.library.utils.model.ResponseObject;
import com.example.library.utils.services.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class GenericController<Service extends GenericService<Dao, Domain, Id>,
        Dao extends GenericDao<Domain, Id> ,
        Domain extends GenericDomain,
        Id extends Serializable> {

    @Autowired
    protected Service service;

    @Autowired
    protected MessageSourceService messageSourceService;
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(/*@ParameterName(value = "pagination", required = false)*/@ModelAttribute Pagination pagination,
                                                                                                   @RequestParam(required = false) int... status) {

        Page<Domain> page = service.getAll(pagination, status);
        Map<String, Object> count = new HashMap<>();
        if (page != null) {
            count.put("count", page.getTotalElements());

            return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.Data_Fetched_SUCCESS, page.getContent(), count);
        } else {
            return ResponseObject.FAILED_RESPONSE(ResponseMessage.Data_Fetched_SUCCESS, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/by-id")
    public ResponseEntity<Object> getById(@RequestParam("id") Id id) {
        Domain model = service.getById(id);

        return model != null ?
                ResponseObject.SUCCESS_RESPONSE(ResponseMessage.Data_Fetched_SUCCESS, model, null) :
                ResponseObject.FAILED_RESPONSE(ResponseMessage.Data_Fetched_FAILED, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> save(@ParameterName("domain") Domain domain) throws Exception {
        Domain savedDomain = service.merge(domain);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.ADDED_SUCCESS, savedDomain, null);
    }
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@ParameterName("domain") Domain domain) throws Exception {
        if (domain.getId() == null || service.getById((Id) domain.getId()) ==null) {
            throw new ResourceNotFoundException(null);
        }
        Domain updatedDomain = service.merge(domain);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.Update_SUCCESS, updatedDomain, null);
    }
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@RequestParam("id") Id id) throws Exception {

        Boolean success = service.delete(id);
        return success ?
                ResponseObject.SUCCESS_RESPONSE(ResponseMessage.Data_Deleted_SUCCESS, null, null) :
                ResponseObject.FAILED_RESPONSE(ResponseMessage.Data_Deleted_FAILED, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
