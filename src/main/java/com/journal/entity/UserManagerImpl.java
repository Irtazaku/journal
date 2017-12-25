package com.journal.entity;


import com.journal.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
@Transactional
public class UserManagerImpl implements UserManager {

    @PersistenceContext()
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Util util;

    //@Override
    public User getUserByAuthenticationToken(String authenticationToken) {
        TypedQuery<User> query = entityManager
                .createNamedQuery("user.getUserByAuthenticationToken", User.class)
                .setParameter("authenticationToken", authenticationToken);

        List<User> users = query.getResultList();
        return !users.isEmpty() ? users.get(0) : null;
    }

    @Override
    public User getUserByUserId(Integer userId) {
        TypedQuery<User> query = entityManager
                .createNamedQuery("user.getUserByUserId", User.class)
                .setParameter("userId", userId);

        List<User> users = query.getResultList();
        return !users.isEmpty() ? users.get(0) : null;
    }

    @Override
    public User merge(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User persist(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        TypedQuery<User> query = entityManager
                .createNamedQuery("user.getUserByUsernameAndPassword", User.class)
                .setParameter("username", username)
                .setParameter("password", password);

        List<User> users = query.getResultList();
        if(users.size() == 1){
            User user = users.get(0);
            user.setToken(util.generateToken());
            user = userRepository.save(user);
            return user;
        }
        return  null;
    }

}
