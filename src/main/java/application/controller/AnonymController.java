package application.controller;

import application.model.Blog;
import application.model.BlogEntry;
import application.model.Comment;
import application.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class AnonymController {

    private VisitorService service;

    @Autowired
    public AnonymController(VisitorService service) {
        this.service = service;
    }

    @GetMapping(value = {"/", "/home"})
    public String getIndex() {
        return "index";
    }

    @GetMapping("/blogs/{blogId}")
    public List<Blog> getBlogById(@PathVariable Long blogId) { //TODO handling non-existing IDs
        if (blogId == null)
            return service.loadAllBlog();

        return Collections.singletonList(service.loadBlogById(blogId));
    }

    @GetMapping("/blogs/{blogId}/entries/{entryId}")
    public List<BlogEntry> getBlogEntryById(@PathVariable Long blogId, @PathVariable Long entryId) { //TODO handling non-existing IDs
        if (entryId == null)
            return service.loadAllEntryFromBlog(blogId);

        return Collections.singletonList(service.loadBlogEntryById(entryId));
    }

    @GetMapping("/comments")
    public List<Comment> getCommentsFromEntry(@RequestParam() Long entryId) { //TODO handling non-existing IDs
        return service.loadAllCommentFromEntry(entryId);
    }

    @GetMapping("/replies")
    public List<Comment> getRepliesFromComment(@RequestParam() Long commentId) { //TODO handling non-existing IDs
        return service.loadReplyById(commentId);
    }

}
