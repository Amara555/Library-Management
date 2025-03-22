package com.example.library.Dao;

import com.example.library.domain.Book;
import com.example.library.domain.BorrowingRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface BorrowingRecordDao extends GenericDao<BorrowingRecord,Integer> {

    @Query("SELECT T FROM  BorrowingRecord T " +
            "WHERE T.bookId=:bookId " +
            " AND T.patronId=:patronId " +
            "ORDER BY T.creationDate DESC ")
   List< BorrowingRecord> findLatsBorrowingRecordByBookIdAndPatronId(Integer bookId,Integer patronId);
    @Modifying
    @Query("UPDATE BorrowingRecord SET returnDate = :returnDate WHERE id =:id ")
    void updateReturnDate(Date returnDate, Integer id);
}
