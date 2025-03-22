package com.example.library.Services;

import com.example.library.Dao.RoleDao;
import com.example.library.Services.Generic.GenericService;
import com.example.library.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService  extends GenericService<RoleDao, Role,Integer> {
    public Role FindRoleByRoleName(String name){
        try {
            return dao.fineRoleByName(name);
        }catch (Exception e){
            throw e;
        }
    }
    public List<Role> findAll(){
        return dao.findAll();
    }
}
