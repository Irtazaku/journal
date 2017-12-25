package com.journal.dto;

import com.journal.entity.User;

import java.io.Serializable;

/**
 * Created by Venturedive on 11/09/2017.
 */
public class UserDto implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String token;
    private String type;

    public UserDto() {
    }

    public UserDto(Integer id, String username, String password, String name, String email, String token, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.token = token;
        this.type = type;
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


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User asEntity(){

        return new User(this.id, this.username, this.password, this.name, this.email, this.token, this.type);
    }
}
