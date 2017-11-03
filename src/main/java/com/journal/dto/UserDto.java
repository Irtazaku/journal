package com.journal.dto;

import com.journal.entity.User;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String email;

    public UserDto() {
    }

    public UserDto(Integer id, String username, String password, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User asEntity(){
        return new User(this.id, this.username, this.password, this.name, this.email);
    }
}
