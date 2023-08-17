package com.github.matsgemmeke.sandbox.service;

import com.github.matsgemmeke.sandbox.model.User;
import com.github.matsgemmeke.sandbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        repository.findAll().forEach(users::add);

        return users;
    }

    public User getUser(int id) {
        return repository.findById(id).orElse(null);
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }
}
