package de.ait.javalessons.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// maybe doesn't work. check:
// https://github.com/IgorITZimmermann/developmentGr42/blob/main/DevelopmentGr42/src/test/java/de/ait/javalessons/controller/ProductControllerIT.java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OnlineStoreControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Checking that the public endpoint is available to everyone and returns the correct content")
    void getListOfProductsTest() throws Exception {
        mockMvc.perform(get("/products/public/list"))
                .andExpect(status().isOk())
                .andExpect(content().string("Apple, Banana, Mango, Orange"));
    }

    @Nested
    @DisplayName("Tests for endpoint /products/customer/cart")
    class getCustomerCartTests {

        @Test
        @DisplayName("When a user is authorized with the role ")
    }


}
