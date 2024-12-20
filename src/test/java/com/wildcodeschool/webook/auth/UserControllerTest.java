package com.wildcodeschool.webook.auth;

import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    public Cookie loginHelper() throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("email", "louli@mail.com");
        jo.put("password", "L0ul!123");

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .content(jo.toString())
                        .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult result = resultActions.andReturn();
        // Extract token from the Set-Cookie header
        String setCookieHeader = result.getResponse().getHeader("Set-Cookie");
        Cookie authCookie = null;
        if (setCookieHeader != null) {
            String token = setCookieHeader.split("token=")[1].split(";")[0];
            authCookie = new Cookie("token", token);
            authCookie.setPath("/");
            authCookie.setHttpOnly(true);
        }
        return authCookie;
    }

    @Test
    @Transactional
    public void testDeleteUser() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/users/1")
                                .cookie(loginHelper())
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
