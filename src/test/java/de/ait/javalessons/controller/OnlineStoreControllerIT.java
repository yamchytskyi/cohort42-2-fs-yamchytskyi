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
        @DisplayName("When a user is authorized with the role USER, status 200 and correct content")
        @WithMockUser(username = "testCustomer", roles = {"CUSTOMER"})
        void getCustomerCartTestAsCustomer() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Apple: 3 kg, Banana: 2 kg"));
        }

        @Test
        @DisplayName("When a user isn't authorized")
        void getCustomerCartTestAsAnonymous() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("Tests for endpoint /products/manager/add")
    class addProductToTheStoreTests {

        @Test
        @DisplayName("When a user is authorized with the role MANAGER, status 200 and the correct content")
        @WithMockUser(username = "manager", roles = {"MANAGER"})
        void addProductToTheStoreTestAsManager() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("What product do you want to add to the store?"));
        }

        @Test
        @DisplayName("Test when a user doesn't have role MANAGER, returns error 403")
        @WithMockUser(username = "customer", roles = {"CUSTOMER"})
        void addProductToTheStoreTestAsCustomer() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("When the user isn't authorized, returns error 401 or 403")
        void addProductToTheStoreAsAnonymousTest() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().is3xxRedirection());
        }
    }


}
