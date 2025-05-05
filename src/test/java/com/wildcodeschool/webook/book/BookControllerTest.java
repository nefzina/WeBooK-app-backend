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
import org.springframework.mock.web.MockMultipartFile;
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
        return result.getResponse().getCookie("token");
    }

    @Test
    public void testAddBook() throws Exception {

        JSONObject category = new JSONObject();
        category.put("id", 1);
        category.put("type", "Bandes dessinées");

        JSONObject book = new JSONObject();
        book.put("title", "maybe someday");
        book.put("author", "colleen hoover");
        book.put("isbn", "1649374178");
        book.put("bookCategory", category);
        // owner is set in the controller from the cookie data

        // Create MockMultipartFile for the book JSON
        MockMultipartFile bookPart = new MockMultipartFile(
                "book",
                "book.json",
                MediaType.APPLICATION_JSON_VALUE,
                book.toString().getBytes()
        );

        // Create MockMultipartFile for the cover image
        MockMultipartFile imagePart = new MockMultipartFile(
                "file",
                "cover.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "dummy image content".getBytes()
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/books")
                                .file(bookPart)
                                .file(imagePart)
                                .cookie(loginHelper())
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("maybe someday"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("colleen hoover"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("1649374178"))
        ;
    }

    @Test
    public void testAddBookWithInvalidData() throws Exception {

        JSONObject category = new JSONObject();
        category.put("id", 1);
        category.put("type", "Bandes dessinées");

        JSONObject book = new JSONObject();
        book.put("title", "ma*");
        book.put("author", "");
        book.put("isbn", "1649");
        book.put("bookCategory", category);
        // owner is set in the controller from the cookie data

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/books")
                                .cookie(loginHelper())
                                .content(book.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
        ;
    }
}
