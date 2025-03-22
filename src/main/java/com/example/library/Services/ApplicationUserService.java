package com.example.library.Services;

import com.example.library.Dao.ApplicationUserDao;
import com.example.library.Services.Generic.GenericService;
import com.example.library.domain.ApplicationUser;
import com.example.library.domain.Role;
import com.example.library.exeption.SpecificExceptions.ResourceAlreadyFoundException;
import com.example.library.exeption.SpecificExceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService extends GenericService<ApplicationUserDao, ApplicationUser,Integer> {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;


    public ApplicationUser CheckIfUserNameIsFounded(String username){
        try {
            ApplicationUser applicationUser = dao.findByUserName(username);
          return applicationUser;
        }catch (Exception e){
            throw e;
        }

    }

public ApplicationUser save(ApplicationUser applicationUser) throws Exception {
    if(CheckIfUserNameIsFounded(applicationUser.getUsername())==null){
        if (applicationUser.getRole()==null){
            Role role = roleService.FindRoleByRoleName("Admin");
            applicationUser.setRole(role);
        }
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        return merge(applicationUser);
    }
    else throw new ResourceAlreadyFoundException(null);

}

public ApplicationUser findByUserName(String userName){
        ApplicationUser user= dao.findByUserName(userName);
        if (user!=null){
            return user;
        }
        else throw new  ResourceNotFoundException("userName is not Correct");
}

}
