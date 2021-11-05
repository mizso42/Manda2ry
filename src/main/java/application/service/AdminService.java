package application.service;

import application.model.Blogger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminService implements UserDetailsService {

    @PersistenceContext
    private EntityManager manager;


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return manager
                .createQuery("SELECT blogger FROM Blogger blogger WHERE blogger.username = :name", Blogger.class) //TODO everything but password
                .setParameter("name", username)
                .getSingleResult();
    }

    @Transactional
    public List<Blogger> loadAllBlogger() {
        return manager.createQuery("SELECT blogger FROM Blogger blogger", Blogger.class).getResultList();
    }
}
