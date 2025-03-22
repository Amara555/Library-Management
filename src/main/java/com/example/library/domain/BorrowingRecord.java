package com.example.library.domain;

import com.example.library.domain.generic.GenericDomain;
import com.example.library.domain.generic.LocalDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BorrowingRecord extends LocalDomain {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer bookId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer patronId;
    @Column(name = "Borrow_Date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date borrowDate;
    @Column(name = "Return_Date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    Date returnDate;
    @ManyToOne
    @JsonIgnoreProperties("borrowingRecordList")
    @JoinColumn(name = "book_Id", referencedColumnName = "Id", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "Patron_Id")
    @JsonIgnoreProperties("bookList")
    private Patron patron;

    @Override
    public void setDatePreUpdate() {
       this.returnDate = new Date();
    }
}
