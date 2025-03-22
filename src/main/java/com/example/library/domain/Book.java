package com.example.library.domain;

import com.example.library.domain.generic.LocalDomain;
import com.example.library.utils.BookStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "Book")
public class Book extends LocalDomain {

    @Column(name = "Title", nullable = false,unique = true)
    private String title;
    @Column(name = "Author", nullable = false)
    private String author;
    @Column(name = "ISBN", nullable = false,unique = true)
    private String isbn;
    @Column(name = "Book_Status")
    private BookStatus bookStatus;
   @OneToMany(mappedBy = "book")
   Set<BorrowingRecord> borrowingRecordList;

    public Book(String title, String author, String ISBN, Set<BorrowingRecord> borrowingRecordList,BookStatus bookStatus) {
        this.title = title;
        this.author = author;
        this.isbn = ISBN;
        this.borrowingRecordList = borrowingRecordList;
        this.bookStatus = BookStatus.Free;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Date getCreationDate() {
        return super.getCreationDate();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Date getModifiedDate() {
        return super.getModifiedDate();
    }


    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer getCreatorId() {
        return super.getCreatorId();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Integer getModifierId() {
        return super.getModifierId();
    }

}
