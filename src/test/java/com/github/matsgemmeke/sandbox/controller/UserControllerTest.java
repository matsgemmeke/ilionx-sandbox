package com.github.matsgemmeke.sandbox.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matsgemmeke.sandbox.model.User;
import com.github.matsgemmeke.sandbox.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService service;

    @Test
    public void requestingAllUsersCallsService() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).getAllUsers();
    }

    @Test
    public void requestingSingleUserCallsService() throws Exception {
        int userId = 1;

        mvc.perform(MockMvcRequestBuilders.get("/user/" + userId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).getUser(userId);
    }

    @Test
    public void postingUserCallsService() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("test");

        String userJson = objectMapper.writeValueAsString(user);

        mvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());

        verify(service, times(1)).saveUser(any());
    }

    @Test
    public void deletingUserCallsService() throws Exception {
        int userId = 1;

        mvc.perform(MockMvcRequestBuilders.delete("/user/" + userId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteUser(userId);
    }
}
