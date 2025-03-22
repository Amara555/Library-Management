package com.example.library.Dao;

import com.example.library.domain.ApplicationUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicationUserDao extends GenericDao<ApplicationUser,Integer>{
    @Query("SELECT C FROM ApplicationUser C " +
            "WHERE C.username =:userName " )
    ApplicationUser findByUserName(@Param("userName") String userName);


}
