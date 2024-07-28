package com.wildcodeschool.webook.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;
import com.wildcodeschool.webook.book.domain.entity.Category;
import com.wildcodeschool.webook.book.infrastructure.repository.CategoryRepository;
import com.wildcodeschool.webook.fileUpload.domain.entity.Media;
import jakarta.persistence.*;
import jakarta.servlet.http.Cookie;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Cookie loginHelper() throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("email", "apple@mail.com");
        jo.put("password", "Appl€P2e");

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
    public void testAddBook() throws Exception {

        JSONObject category = new JSONObject();
        category.put("id", 1);
        category.put("type", "Bandes dessinées");

        JSONObject book = new JSONObject();
        book.put("name", "maybe someday");
        book.put("author", "colleen hoover");
        book.put("isbn", "1649374178");
        book.put("bookCategory", category);
        // owner is set in the controller from the cookie data

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/books")
                                .cookie(loginHelper())
                                .content(book.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("maybe someday"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("colleen hoover"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1649374178"))
        ;
    }
}
