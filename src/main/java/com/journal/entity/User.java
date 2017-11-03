package com.journal.entity;


import com.journal.dto.UserDto;

import javax.persistence.*;

/**
 * Created by Venturedive on 10/29/2017.
 */

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private String name;
    private String email;

    public User(){
    }

    public User(Integer id, String email, String username, String password, String name) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
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

    public UserDto asDto() {
        return new UserDto(this.id, this.username, this.getPassword(), this.name, this.email);
    }
}
