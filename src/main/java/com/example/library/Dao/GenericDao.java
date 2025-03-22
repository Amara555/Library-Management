package com.example.library.Dao;

import com.example.library.domain.generic.GenericDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericDao<T extends GenericDomain, IdClass> extends JpaRepository<T, IdClass>, Serializable, JpaSpecificationExecutor<T> {
    @Query("Select t From #{#entityName} t #{#pageRequest}")
    Page<T> findAllByStatusWithPagination(Pageable pageRequest, int[] recordStatus);

    @Query("Select t From #{#entityName} t  #{#pageRequest}")
    Page<T> findAll(Pageable pageRequest);

//
//    @Modifying
//    @Query("UPDATE #{#entityName} t SET t.returnDate" +
//            "WHERE .txt.id=:id")
//    void update(@Param("id") IdClass id);

}