package application.service;

import application.model.Blog;
import application.model.BlogEntry;
import application.model.Comment;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class VisitorService {

    @PersistenceContext
    private EntityManager manager;


    @Transactional
    public List<Blog> loadAllBlog() {
        return manager.createQuery("SELECT blog FROM Blog blog", Blog.class).getResultList();
    }

    @Transactional
    public Blog loadBlogById(Long blogId) {
        return manager.createQuery("SELECT blog FROM Blog blog WHERE blog.id = :id", Blog.class)
                .setParameter("id", blogId).getSingleResult();
    }

    @Transactional
    public List<BlogEntry> loadAllEntryFromBlog(long blogId) {
        return manager
                .createQuery("SELECT blogEntry FROM Blog blog JOIN blog.blogEntries blogEntry " +
                        "WHERE blog.id = :id", BlogEntry.class)
                .setParameter("id", blogId).getResultList();
    }

    @Transactional
    public BlogEntry loadBlogEntryById(long entryId) {
        return manager.createQuery("SELECT blogEntry FROM BlogEntry blogEntry WHERE blogEntry.id = :id", BlogEntry.class)
                .setParameter("id", entryId).getSingleResult();
    }

    @Transactional
    public List<Comment> loadAllCommentFromEntry(long entryId) {
        return manager
                .createQuery("SELECT comment FROM BlogEntry entry JOIN entry.comments comment " +
                        "WHERE entry.id = :id AND comment.previous IS NULL", Comment.class)
                .setParameter("id", entryId).getResultList();
    }

    @Transactional
    public List<Comment> loadReplyById(long commentId) {
        return manager.createQuery("SELECT comment FROM Comment comment WHERE comment.previous = :id", Comment.class) //TODO will it work this way?
                .setParameter("id", commentId).getResultList();
    }


}
