package com.example.library.Dao;

import com.example.library.domain.Book;
import org.springframework.data.jpa.repository.Query;

public interface BookDao extends GenericDao<Book,Integer>{

    @Query("SELECT T FROM  Book T " +
            "WHERE T.title=:title")
    Book findBookByTitle(String title);
}
