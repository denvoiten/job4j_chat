package ru.chat.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.chat.Job4jChatApplication;
import ru.chat.domain.Role;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jChatApplication.class)
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleController roles;

    @Test
    @WithMockUser
    public void whenFindAll() throws Exception {
        var role1 = Role.of(1, "ROLE_USER");
        var role2 = Role.of(2, "ROLE_ADMIN");

        when(roles.findAll()).thenReturn(Arrays.asList(role1, role2));
        mockMvc.perform(get("/roles/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("ROLE_USER")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("ROLE_ADMIN")));
    }

    @Test
    @WithMockUser
    public void whenAdd() throws Exception {
        Role roleModerator = Role.of(3, "ROLE_MODERATOR");
        when(roles.create(any(Role.class))).thenReturn(new ResponseEntity<>(roleModerator, HttpStatus.OK));
        mockMvc.perform(post("/roles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"ROLE_MODERATOR\"}"))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Role> arg = ArgumentCaptor.forClass(Role.class);
        verify(roles).create(arg.capture());
        assertEquals(arg.getValue().getName(), "ROLE_MODERATOR");
    }
}