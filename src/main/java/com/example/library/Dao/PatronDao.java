package com.example.library.Dao;

import com.example.library.domain.ApplicationUser;
import com.example.library.domain.Patron;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatronDao extends GenericDao<Patron,Integer>{
    @Query("SELECT C FROM Patron C " +
            "WHERE C.email =:email " )
    Patron findByUEmail(@Param("email") String email);


}
