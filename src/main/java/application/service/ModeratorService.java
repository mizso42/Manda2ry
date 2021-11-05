package application.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ModeratorService {

    @PersistenceContext
    private EntityManager manager;
}
