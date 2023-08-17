package com.github.matsgemmeke.sandbox.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.matsgemmeke.sandbox.model.User;
import com.github.matsgemmeke.sandbox.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(UserService.class)
public class UserServiceTest {

    @MockBean
    private UserRepository repository;
    @Autowired
    private UserService service;

    @Test
    public void shouldReturnAllUsersFromRepository() {
        Iterable<User> users = Collections.singletonList(new User());

        when(repository.findAll()).thenReturn(users);

        List<User> result = service.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void returnsSingleUserFromRepository() {
        int userId = 1;
        User user = new User();
        Optional<User> optional = Optional.of(user);

        when(repository.findById(userId)).thenReturn(optional);

        User result = service.getUser(userId);

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void returnsNullWhenFindingWithUnknownId() {
        int userId = 1;
        Optional<User> optional = Optional.empty();

        when(repository.findById(userId)).thenReturn(optional);

        User result = service.getUser(userId);

        assertThat(result).isNull();
    }

    @Test
    public void savingUserCallsRepositoryFunction() {
        User user = new User();

        service.saveUser(user);

        verify(repository, times(1)).save(user);
    }

    @Test
    public void deletingUserCallsRepositoryFunction() {
        int userId = 1;

        service.deleteUser(1);

        verify(repository, times(1)).deleteById(userId);
    }
}
