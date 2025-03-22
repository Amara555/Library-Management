package com.example.library.utils.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class ResponseObject<Domain> implements Serializable {

    public enum ReturnCode {
        INFO("Info"),
        SUCCESS("Success"),
        FAILED("Failed"),
        WARNING("Warning");

        private final String returnCode;

        ReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnCode() {
            return returnCode;
        }
    }
    public enum Text {
        BADCREDENTIALS("Bad Request, 'domainName' and 'password' and 'systemName' are required"),
        LOGGEDIN("Logged in successfully"),
        LOGINFAILED("Log in failed"),
        ADDEDSUCCESSFULY("addedSuccessfully"),
        ADDINGFAILED("addingFAILED"),
        UPDATEDSUCCESSFULY("updatedSuccessfully"),
        UPDATINGFAILED("updatingFAILED"),
        VALIDATIONFAILD("validation failed"),
        FETCHEDSUCCESSFULY("Fetched Successfully"),
        FETCHINGFAILED("Fetching failed"),
        DELETEDSUCCESSFULLY("Deleted Successfully"),
        DELETINGFAILED("Deleting failed"),
        DISPATCHEDSUCCESSFULLY("DISPATCHED SUCCESSFULLY"),
        DISPATCHEDFAILED("DISPATCHED FAILED"),
        LOCKEDSUCCESSFULY("Locked Successfully"),
        LOCKINGFAILED("There is a merge operation you can not update right now"),
        MERGINGFAILED("You can not merge right now try again"),
        IMAGEANDPDF("Image and Pdf"),
        ERROR("Submit completed with some errors, please review submitted cards");

        private String text;

        Text(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private String message;
    private List<?> list;
    private Object model;
    private String returnCode;
    private Map<?, ?> extraData;

    public ResponseObject() {
        super();
        this.extraData = new HashMap<>();
    }
    private ResponseObject(Object responseToBereturned, Map<String, Object> extraData) {
        List list = null;
        Object model = null;
        if (responseToBereturned != null && responseToBereturned instanceof List)
            list = (List) responseToBereturned;
        else
            model = responseToBereturned;
        this.setList(list);
        this.setModel((Domain) model);
        this.setExtraData(extraData);

    }

    public static ResponseEntity<Object> SUCCESS_RESPONSE(String message, List<?> list, Map<?, ?> extraData) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage(message);
        responseObject.setList(list);
        responseObject.setModel(null);
        responseObject.setReturnCode(ReturnCode.SUCCESS.getReturnCode());
        responseObject.setExtraData(extraData);
        return new ResponseEntity<>(
                responseObject,
                HttpStatus.OK
        );
    }

    public static ResponseEntity<Object> SUCCESS_RESPONSE(String message, Object model, Map<?, ?> extraData) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage(message);
        responseObject.setList(null);
        responseObject.setModel(model);
        responseObject.setReturnCode(ReturnCode.SUCCESS.getReturnCode());
        responseObject.setExtraData(extraData);
        return new ResponseEntity<>(
                responseObject,
                HttpStatus.OK
        );
    }

    public static ResponseEntity<Object> FAILED_RESPONSE(String message, Map<?, ?> extraData, HttpStatus httpStatus) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage(message);
        responseObject.setList(null);
        responseObject.setModel(null);
        responseObject.setReturnCode(ReturnCode.FAILED.getReturnCode());
        responseObject.setExtraData(extraData);
        return new ResponseEntity<>(
                responseObject,
                httpStatus
        );
    }
    public static ResponseObject ADDED_SUCCESS(Object response, Map<String, Object> extraData) {
        ResponseObject responseObject = new ResponseObject(response,extraData);
        responseObject.setMessage(ResponseObject.Text.ADDEDSUCCESSFULY.getText());
        responseObject.setReturnCode(ReturnCode.SUCCESS.getReturnCode());
        return responseObject;
    }
}
