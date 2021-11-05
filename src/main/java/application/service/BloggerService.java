package application.service;

import application.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
public class BloggerService implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;

    private PasswordEncoder encoder;

    @Autowired
    public BloggerService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return manager
                .createQuery("SELECT blogger" +
                        /*".id, blogger.username, blogger.emilAddress, blogger.pic, " +
                        "blogger.authority, blogger.regTime, blogger.isLocked" +
                        ", blogger.blogs, blogger.comments" +*/
                        " FROM Blogger blogger WHERE blogger.username = :name", Blogger.class) //TODO everything but password
                .setParameter("name", username)
                .getSingleResult();
    }

    public Blogger getLoggedInBlogger() throws UserPrincipalNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof Blogger) {
                return (Blogger) principal;
            }
        }

        throw new UserPrincipalNotFoundException("No user logged in");
    }

    //TODO scrap this
    @Transactional
    public boolean registerDummy(String auth) {
        try {
            String userPwd = encoder.encode("mizso");

            Blogger blogger = new Blogger("mizso", "mizso@emil.com", userPwd);

            blogger.setAuthority(UserRole.valueOf(auth.toUpperCase()));

            manager.persist(blogger);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
