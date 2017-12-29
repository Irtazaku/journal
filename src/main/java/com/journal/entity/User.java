package com.journal.entity;


import com.journal.dto.UserDto;
import com.journal.util.Util;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Venturedive on 10/29/2017.
 */

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "user.getUserByAuthenticationToken",
                query = " SELECT u" +
                        " FROM User u " +
                        " WHERE u.token = :authenticationToken "),
        @NamedQuery(name = "user.getUserByUserId",
                query = " SELECT u" +
                        " FROM User u " +
                        " WHERE u.id = :userId "),
        @NamedQuery(name = "user.getUserByUsernameAndPassword",
                query = " SELECT u" +
                        " FROM User u " +
                        " WHERE u.username = :username " +
                        " AND u.password = :password")
})
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    private String name;

    private String email;

    @Size(min = 32, max = 32)
    @Column(unique = true)
    private String token;

    @Column(nullable = false)
    private String type = "user";

    public User(){
    }

    public User( String email, String username, String password, String name, String type) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.token = Util.generateToken();
        this.type = type;
    }
    public User(Integer id, String email, String username, String password, String name, String token, String type) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.token = token;
        this.type =type;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserDto asDto() {
        return new UserDto(this.id, this.username, null, this.name, this.email, this.token, this.type);
    }
}
