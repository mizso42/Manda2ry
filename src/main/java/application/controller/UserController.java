package application.controller;

import application.exeption.MyException;
import application.model.Blog;
import application.model.BlogEntry;
import application.model.Blogger;
import application.model.Comment;
import application.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/user/mydata")
    public String getUserDetails() {
        try {
            return service.getLoggedInBlogger().getUsername(); //TODO also return other details
        } catch (UserPrincipalNotFoundException e) {
            return e.getName();
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_OWN_DATA')")
    @PostMapping("/user/mydata")
    public List<String> postUserDetails(@RequestBody Map<String, String> dataMap) {
        try {
            return service.updateOwnData(dataMap);
        } catch (MyException e) {
            return Collections.singletonList(e.getMessage());
        } catch (UserPrincipalNotFoundException e) {
            return Collections.singletonList(e.getName());
        }
    }

}
