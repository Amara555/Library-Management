package com.example.library.Controller;

import com.example.library.Controller.Generic.GenericController;
import com.example.library.Dao.BookDao;
import com.example.library.Services.BookService;
import com.example.library.domain.Book;
import com.example.library.utils.annotation.ParameterName;
import com.example.library.utils.constants.ResponseMessage;
import com.example.library.utils.model.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/books")
public class BookController extends GenericController<BookService, BookDao, Book,Integer> {
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Object> save(@ParameterName("book") Book book) throws Exception {
        Book newBook =  service.save(book);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.ADDED_SUCCESS, newBook, null);
    }
    @RequestMapping( path = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@ParameterName("book") Book book) throws Exception {
        Book newBook =  service.save(book);
        return ResponseObject.SUCCESS_RESPONSE(ResponseMessage.ADDED_SUCCESS, newBook, null);
    }
}
