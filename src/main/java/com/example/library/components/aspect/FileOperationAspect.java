package com.example.library.components.aspect;

import com.example.library.Services.*;
import com.example.library.domain.*;
import com.example.library.security.model.CustomUserDetails;
import com.example.library.utils.annotation.Operation;
import com.example.library.utils.services.OperationActions;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint ;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Aspect
@Component
public class FileOperationAspect {


private final BookService bookService;
private final PatronService patronService;
private final ActionService actionService;
    /**
     * Log the operation details into the FileVersion table only if the transaction succeeds.
     */
   @AfterReturning(value = "@annotation(operation)")
   @Transactional
    public void trackOperation( JoinPoint joinPoint,  Operation operation) throws Exception {
        Object[] args = joinPoint.getArgs();
       Integer bookId = (Integer) args[0];
       Integer patronId = (Integer) args[1];
       ApplicationUser user= CustomUserDetails.getCurrentInstance().getApplicationUser();
        if (Objects.equals(operation.type().name(), OperationActions.Borrow.name())){

            Book book = bookService.getById(bookId);
            Patron patron = patronService.getById(patronId);
            createNewBorrowingRecord(book,patron,operation);
        }
        else {
            BorrowingRecord borrowingRecord = actionService.getLatestBorrowingRecordByBookIdAndPatronId(bookId,patronId);
            borrowingRecord.setReturnDate(new Date());
            actionService.update(borrowingRecord);
        }
    }

    private void createNewBorrowingRecord(Book book, Patron patron, Operation operation) throws Exception {
       BorrowingRecord borrowingRecord = new BorrowingRecord();

       borrowingRecord.setBook(book);
       borrowingRecord.setPatron(patron);
       borrowingRecord.setBookId(book.getId());
       borrowingRecord.setPatronId(patron.getId());
       if (operation.type() == OperationActions.Borrow)
          borrowingRecord.setBorrowDate(new Date());

       actionService.merge(borrowingRecord);
    }


}
