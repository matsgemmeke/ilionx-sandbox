package com.github.matsgemmeke.sandbox.repository;

import com.github.matsgemmeke.sandbox.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
