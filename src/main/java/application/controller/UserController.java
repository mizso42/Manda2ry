package application.controller;

import application.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.List;

@RestController
public class UserController {

    BloggerService service;

    @Autowired
    public UserController(BloggerService service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/home"})
    public String getIndex() {
        return "index";
    }

    @GetMapping("/register/{role}")
    public String registerDummy(@PathVariable String role) {
        if (service.registerDummy(role))
            return "OK";
        return "not OK";
    }

    @PreAuthorize("hasAuthority('READ_MY_DATA')")
    @GetMapping("user/mydata")
    public String getUserDetails() {
        try {
            return service.getLoggedInBlogger().getUsername(); //TODO also return other details
        } catch (UserPrincipalNotFoundException e) {
            return e.getName();
        }
    }

    @PreAuthorize("hasAuthority('READ_OTHERS_DATA')")
    @GetMapping("/user/{username}")
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

}
