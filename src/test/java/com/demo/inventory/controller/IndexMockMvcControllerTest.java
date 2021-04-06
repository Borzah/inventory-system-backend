package com.demo.inventory.controller;

import com.demo.inventory.admin.dto.UserStatisticsResponse;
import com.demo.inventory.admin.service.StatisticsService;
import com.demo.inventory.configuration.setup.StartDataRunner;
import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.service.CategoryService;

import com.demo.inventory.user.dto.RegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexMockMvcControllerTest {

    // Added to avoid errors
    @MockBean
    private StartDataRunner startDataRunner;

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    private static final String CATEGORIES = "/categories";
    private static final String STATISTICS = "/statistics";
    private static final String REGISTER = "/users/register";

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void adminUrlShouldBeAccessibleToAdmin() throws Exception {
        List<UserStatisticsResponse> expected = List.of(new UserStatisticsResponse());
        when(statisticsService.getUserStatistics()).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders.get(STATISTICS).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "USER")
    public void adminUrlShouldNotBeAccessibleToUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(STATISTICS).accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    public void userUrlShouldNotBeAccessibleToAdmin() throws Exception {
        when(categoryService.getAllCategoriesByUserId(Mockito.anyLong()))
                .thenReturn(List.of(new CategoryDto()));
        mvc.perform(MockMvcRequestBuilders.get(CATEGORIES).accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void registerUrlShouldBeAccessibleToEveryOne() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("test@test.test");
        registerDto.setPassword("test");
        mvc.perform(post(REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());
    }
}
