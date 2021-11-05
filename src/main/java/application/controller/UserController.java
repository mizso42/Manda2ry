package application.controller;

import application.model.Blog;
import application.model.BlogEntry;
import application.model.Comment;
import application.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
public class UserController {

    BloggerService service;

    @Autowired
    public UserController(BloggerService service) {
        this.service = service;
    }

    //TODO scrap this
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

}
