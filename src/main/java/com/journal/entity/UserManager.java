package com.journal.entity;

public interface UserManager {

    public User getUserByAuthenticationToken(String authenticationToken);

    public User getUserByUserId(Integer userId);

    public User merge(User user);

    public User persist(User n);

    User getUserByUsernameAndPassword(String username, String password);
}
