package com.github.matsgemmeke.sandbox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matsgemmeke.sandbox.model.Competition;
import com.github.matsgemmeke.sandbox.model.CompetitionEntry;
import com.github.matsgemmeke.sandbox.model.User;
import com.github.matsgemmeke.sandbox.model.UserDTO;
import com.github.matsgemmeke.sandbox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final HttpService httpService;
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository repository;

    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();

        repository.findAll().forEach(u -> users.add(new UserDTO(u.getName(), u.getEmail())));

        Competition competition = httpService.get("/competition", Competition.class);

        users.forEach(u -> {
            CompetitionEntry entry = new CompetitionEntry();
            entry.setCompetition(competition);
            entry.setWeightClass("89kg");

            u.getEntries().add(entry);
        });

        return users;
    }

    public UserDTO getUser(int id) {
        Optional<User> optional = repository.findById(id);

        if (optional.isEmpty()) {
            return null;
        }

        User user = optional.get();

        return new UserDTO(user.getName(), user.getEmail());
    }

    public void saveUser(User user) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("sandbox", objectMapper.writeValueAsString(user));
    }

    public void deleteUser(int id) {
        repository.deleteById(id);
    }
}
