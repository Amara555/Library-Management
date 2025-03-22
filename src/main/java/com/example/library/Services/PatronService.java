package com.example.library.Services;

import com.example.library.Dao.ApplicationUserDao;
import com.example.library.Dao.PatronDao;
import com.example.library.Services.Generic.GenericService;
import com.example.library.domain.ApplicationUser;
import com.example.library.domain.Book;
import com.example.library.domain.Patron;
import com.example.library.domain.Role;
import com.example.library.exeption.SpecificExceptions.ResourceAlreadyFoundException;
import com.example.library.exeption.SpecificExceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatronService extends GenericService<PatronDao, Patron,Integer> {



    public Patron CheckIfEmailIsFounded(String email){
        try {
            Patron patron = dao.findByUEmail(email);
            return patron;
        }catch (Exception e){
            throw e;
        }

    }
    public Patron save(Patron patron) throws Exception {
        if(CheckIfEmailIsFounded(patron.getEmail())==null){

            return merge(patron);
        }
        else throw new ResourceAlreadyFoundException(null);
    }

}
