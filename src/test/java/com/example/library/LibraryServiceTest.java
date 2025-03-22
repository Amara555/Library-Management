package com.example.library;

import com.example.library.Services.ActionService;
import com.example.library.Services.BookService;
import com.example.library.Services.PatronService;
import com.example.library.domain.Book;
import com.example.library.domain.BorrowingRecord;
import com.example.library.domain.Patron;
import com.example.library.utils.BookStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private ActionService actionService;

    @Test
    public void testReturnBook_Success() throws Exception {
        Book book = new Book("Java Programming", "Amara","1111" ,new HashSet<>(), BookStatus.Borrowed);
        Patron patron = new Patron( "Amara","shujaa","amara@gmail.com","0981255099",new HashSet<>());
        BorrowingRecord record = new BorrowingRecord(1,1,  new Date(),null,book, patron);

        when(bookService.getById(1)).thenReturn(book);
        when(patronService.getById(1)).thenReturn(patron);
        when(actionService.getLatestBorrowingRecordByBookIdAndPatronId(1, 1)).thenReturn(record);

        actionService.returnBook(1, 1);

        assertEquals(BookStatus.Free, book.getBookStatus());
        assertNotNull(record.getReturnDate());
    }
}

