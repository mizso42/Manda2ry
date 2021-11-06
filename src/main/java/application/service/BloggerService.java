package application.service;

import application.exeption.MyException;
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
import java.lang.reflect.Field;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BloggerService implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;

    private final PasswordEncoder encoder;

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

    @Transactional
    public List<String> updateOwnData(Map<String, String> dataMap) throws MyException, UserPrincipalNotFoundException {
        List<String> updatedFields;
        Map<String, String> personalUpdate = new HashMap<>();

        for (Field field : Blogger.class.getDeclaredFields()) {
            if (!(field.getName().equals("id") ||
                    field.getName().equals("authority") ||
                    field.getName().equals("regTime") ||
                    field.getName().equals("isLocked") ||
                    field.getName().equals("blogs") ||
                    field.getName().equals("comments")) &&
                    dataMap.containsKey(field.getName())) {

                if (field.getName().equals("password")) {
                    if (dataMap.containsKey("re_password")) {
                        if (dataMap.get(field.getName()).equals(dataMap.get("re_password"))) {
                            dataMap.put(field.getName(), encoder.encode(dataMap.get(field.getName())));
                        } else {
                            throw new MyException("Passwords don't match.");
                        }
                    } else {
                        throw new MyException("Please reinforce your password!");
                    }
                }

                personalUpdate.put((field.getName()), dataMap.get(field.getName()));
            }
        }

        updatedFields = personalUpdate.keySet().stream().toList();

        getLoggedInBlogger().updateBlogger(personalUpdate);

        Blogger thisBlogger = getLoggedInBlogger();

        manager.createQuery("UPDATE Blogger blogger SET blogger = :blogger WHERE blogger.id = :id")
                .setParameter("blogger", thisBlogger)
                .setParameter("id", thisBlogger.getId())
                .executeUpdate();

        return updatedFields;
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
