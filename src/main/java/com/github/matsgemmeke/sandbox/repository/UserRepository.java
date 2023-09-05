package com.github.matsgemmeke.sandbox.repository;

import com.github.matsgemmeke.sandbox.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
