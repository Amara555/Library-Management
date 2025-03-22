package com.example.library.Controller;

import com.example.library.Controller.Generic.GenericController;
import com.example.library.Dao.BookDao;
import com.example.library.Dao.BorrowingRecordDao;
import com.example.library.Services.ActionService;
import com.example.library.Services.BookService;
import com.example.library.domain.Book;
import com.example.library.domain.BorrowingRecord;
import com.example.library.utils.annotation.ParameterName;
import com.example.library.utils.constants.ResponseMessage;
import com.example.library.utils.model.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ActionController extends GenericController<ActionService, BorrowingRecordDao, BorrowingRecord,Integer> {
    @RequestMapping(path = "/borrow/{bookId}/patron/{patronId}", method = RequestMethod.POST)
    public ResponseEntity<Object> borrowBook( @PathVariable("bookId") Integer bookId,
                                        @PathVariable("patronId") Integer patronId) throws Exception {
       service.borrowBook(bookId,patronId);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.Borrowed_Book_Success,null, null);
    }
    @RequestMapping(path = "/return/{bookId}/patron/{patronId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> returnBook( @PathVariable("bookId") Integer bookId,
                                              @PathVariable("patronId") Integer patronId) throws Exception {
        service.returnBook(bookId,patronId);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.Returned_Book_Success,null, null);
    }
//    @RequestMapping( path = "/update", method = RequestMethod.PUT)
//    public ResponseEntity<Object> update(@ParameterName("book") Book book) throws Exception {
//        Book newBook =  service.save(book);
//        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.ADDED_SUCCESS, newBook, null);
//    }
}
