package com.example.library.Services;

import com.example.library.Dao.BookDao;
import com.example.library.Services.Generic.GenericService;
import com.example.library.domain.ApplicationUser;
import com.example.library.domain.Book;
import com.example.library.domain.Role;
import com.example.library.exeption.SpecificExceptions.ResourceAlreadyFoundException;
import com.example.library.utils.BookStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService extends GenericService<BookDao, Book,Integer> {

    public Book CheckIfTitleIsFounded(String title){
        try {
            Book book = dao.findBookByTitle(title);
            return book;
        }catch (Exception e){
            throw e;
        }

    }
    public Book save(Book book) throws Exception {
        if(CheckIfTitleIsFounded(book.getTitle())==null){
            book.setBookStatus(BookStatus.Free);
            return merge(book);
        }
        else throw new ResourceAlreadyFoundException(null);
    }
}
