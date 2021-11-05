package application.controller;

import application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    AdminService service;

    @Autowired
    public AdminController(AdminService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('READ_OTHERS_DATA')")
    @GetMapping("/users/{username}")
    public String getUserDetails(@PathVariable String username) {
        try {
            UserDetails user = service.loadUserByUsername(username);
            return user.getUsername(); //TODO also return other details
        } catch (UsernameNotFoundException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PreAuthorize("hasAuthority('READ_OTHERS_DATA')")
    @GetMapping(value = {"/users", "/users/"})
    public List<String> getAllUserDetails() {
        List<String> nameList = new ArrayList<>();

        service.loadAllBlogger().forEach(blogger -> nameList.add(blogger.getUsername())); //TODO also return other details

        return nameList;
    }
}
