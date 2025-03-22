package com.example.library.Dao;

import com.example.library.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleDao extends GenericDao<Role,Integer>{
    @Query("SELECT R FROM Role R " +
            "WHERE R.name =:name " )
    Role fineRoleByName(@Param("name")String name);
}
