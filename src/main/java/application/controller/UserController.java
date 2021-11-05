package application.controller;

import application.model.Blog;
import application.model.BlogEntry;
import application.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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

    @GetMapping("/blogs/{blogId}")
    public List<Blog> getBlogById(@PathVariable Long blogId) { //TODO handlig non-existing IDs
        if (blogId == null)
            return service.loadAllBlog();

        return Collections.singletonList(service.loadBlogById(blogId));
    }

    @GetMapping("/blogs/{blogId}/entry/{entryId}")
    public List<BlogEntry> getBlogEntryById(@PathVariable Long blogId, @PathVariable Long entryId) { //TODO handlig non-existing IDs
        if (entryId == null)
            return service.loadAllEntryFromBlog(blogId);

        return Collections.singletonList(service.loadBlogEntryById(entryId));
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
