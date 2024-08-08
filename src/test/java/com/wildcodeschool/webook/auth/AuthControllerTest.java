package com.wildcodeschool.webook.auth;

import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;
import com.wildcodeschool.webook.book.infrastructure.repository.CategoryRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("username", "pie");
        jo.put("email", "apple.pie@mail.com");
        jo.put("password", "Appl€P2e");
        jo.put("zip_code", "31300");
        jo.put("city", "toulouse");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/register")
                        .content(jo.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("pie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("apple.pie@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zip_code").value("31300"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("toulouse"));
    }

    @Test
    public void testLogin() throws Exception {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("email", "louli@mail.com");
        jsonUser.put("password", "L0ul!123");

        mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .content(jsonUser.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().exists("token"))
                .andReturn()
        ;
    }
}
