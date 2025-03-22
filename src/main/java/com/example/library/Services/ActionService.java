package com.example.library.Services;

import com.example.library.Dao.BorrowingRecordDao;
import com.example.library.Services.Generic.GenericService;
import com.example.library.domain.Book;
import com.example.library.domain.BorrowingRecord;
import com.example.library.domain.Patron;
import com.example.library.exeption.SpecificExceptions.ResourceAlreadyBorrowedException;
import com.example.library.exeption.SpecificExceptions.ResourceAlreadyFreeException;
import com.example.library.exeption.SpecificExceptions.ResourceNotFoundException;
import com.example.library.utils.BookStatus;
import com.example.library.utils.annotation.Operation;
import com.example.library.utils.services.OperationActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ActionService extends GenericService<BorrowingRecordDao, BorrowingRecord,Integer> {
    @Autowired
    BookService bookService;
    @Autowired
    PatronService patronService;
    @Operation(type = OperationActions.Borrow)
    @Transactional
    public Book borrowBook(Integer bookId, Integer patronId) throws Exception {
        Book book = bookService.getById(bookId);
        Patron patron = patronService.getById(patronId);
        if (book != null && patron != null) {

           if (book.getBookStatus().equals(BookStatus.Free)){
            book.setBookStatus(BookStatus.Borrowed);
          return    bookService.merge(book);
           }
           else {
               throw new ResourceAlreadyBorrowedException("This book has Already borrowed!");
           }
        }
        else throw new  ResourceNotFoundException("book not found or patron not found!");
    }

    @Operation(type = OperationActions.Return)
    @Transactional
    public Book returnBook(Integer bookId, Integer patronId) throws Exception {
        Book book = bookService.getById(bookId);
        Patron patron = patronService.getById(patronId);
        if (book != null && patron != null) {

            if (book.getBookStatus().equals(BookStatus.Borrowed)){
                book.setBookStatus(BookStatus.Free);
            return     bookService.merge(book);
            }
            else {
                throw new ResourceAlreadyFreeException("This book is not borrowed!");
            }
        }
        else throw new  ResourceNotFoundException("book not found or patron not found!");
    }
    public BorrowingRecord getLatestBorrowingRecordByBookIdAndPatronId(Integer bookId, Integer patronId) throws Exception {
         // Fetch only 1 record
       List< BorrowingRecord> borrowingRecord=dao.findLatsBorrowingRecordByBookIdAndPatronId(bookId,patronId);
        if (borrowingRecord!=null){
            return borrowingRecord.get(0);
        }
        else throw new  ResourceNotFoundException("book not found or patron not found!");
    }
    public void update(BorrowingRecord borrowingRecord) throws Exception {
       dao.updateReturnDate(borrowingRecord.getReturnDate(),borrowingRecord.getId());

    }
//    public Book CheckIfTitleIsFounded(String title){
//        try {
//            Book book = dao.findBookByTitle(title);
//            return book;
//        }catch (Exception e){
//            throw e;
//        }
//
//    }
//    public Book save(Book book) throws Exception {
//        if(CheckIfTitleIsFounded(book.getTitle())==null){
//
//            return merge(book);
//        }
//        else throw new ResourceAlreadyFoundException(null);
//    }
}
