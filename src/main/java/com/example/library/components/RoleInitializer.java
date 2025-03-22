package com.example.library.components;

import com.example.library.Services.RoleService;
import com.example.library.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleInitializer implements CommandLineRunner {
    
    @Autowired
    RoleService roleService;
    @Override
    public void run(String... args) throws Exception {
        List<Role> roleList = roleService.findAll();
        if (roleList==null || roleList.isEmpty()){
            ///ADMIN
            Role adminRole =new Role();
            adminRole.setName("Admin");
            adminRole.setEnglishName("Admin");
            adminRole.setArabicName("أدمن");
            roleService.merge(adminRole);

            ///USER
            Role usernRole =new Role();
            usernRole.setName("User");
            usernRole.setEnglishName("User");
            usernRole.setArabicName("مستخدم");
            roleService.merge(usernRole);
        }

    }
}
