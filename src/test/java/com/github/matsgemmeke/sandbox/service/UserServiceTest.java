package com.github.matsgemmeke.sandbox.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matsgemmeke.sandbox.model.Competition;
import com.github.matsgemmeke.sandbox.model.User;
import com.github.matsgemmeke.sandbox.model.UserDTO;
import com.github.matsgemmeke.sandbox.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    private HttpService httpService;
    private ObjectMapper objectMapper;
    private RabbitTemplate rabbitTemplate;
    private UserRepository repository;

    @BeforeEach
    public void setUp() {
        this.httpService = mock(HttpService.class);
        this.objectMapper = mock(ObjectMapper.class);
        this.rabbitTemplate = mock(RabbitTemplate.class);
        this.repository = mock(UserRepository.class);
    }

    @Test
    public void shouldReturnAllUsersFromRepository() {
        Competition competition = new Competition();
        Iterable<User> users = Collections.singletonList(new User());

        when(httpService.get(anyString(), eq(Competition.class))).thenReturn(competition);
        when(repository.findAll()).thenReturn(users);

        UserService service = new UserService(httpService, objectMapper, rabbitTemplate, repository);
        List<UserDTO> result = service.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void returnsNullIfRepositoryReturnsNothing() {
        int userId = 1;
        Optional<User> optional = Optional.empty();

        when(repository.findById(userId)).thenReturn(optional);

        UserService service = new UserService(httpService, objectMapper, rabbitTemplate, repository);
        UserDTO result = service.getUser(userId);

        assertThat(result).isNull();
    }

    @Test
    public void returnsSingleUserFromRepository() {
        int userId = 1;
        User user = new User();
        Optional<User> optional = Optional.of(user);

        when(repository.findById(userId)).thenReturn(optional);

        UserService service = new UserService(httpService, objectMapper, rabbitTemplate, repository);
        UserDTO result = service.getUser(userId);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(user.getName());
    }

    @Test
    public void savingUserCallsQueueFunction() throws Exception {
        User user = new User();

        when(objectMapper.writeValueAsString(user)).thenReturn("test");

        UserService service = new UserService(httpService, objectMapper, rabbitTemplate, repository);
        service.saveUser(user);

        verify(rabbitTemplate, times(1)).convertAndSend("sandbox", "test");
    }

    @Test
    public void deletingUserCallsRepositoryFunction() {
        int userId = 1;

        UserService service = new UserService(httpService, objectMapper, rabbitTemplate, repository);
        service.deleteUser(1);

        verify(repository, times(1)).deleteById(userId);
    }
}
