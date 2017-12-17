package com.journal.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Venturedive on 10/29/2017.
 */
public interface UserAuthRepository extends CrudRepository<User, String> {
}
