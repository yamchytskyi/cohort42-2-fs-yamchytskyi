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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Public endpoint")
    class publicEndpointTests {
        @Test
        @DisplayName("Проверяем, что публичный эндпоинт доступен всем и возвращает правильный контент")
        void testGetPublicInfo() throws Exception {
            mockMvc.perform(get("/employees/public/info"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("User Info, public information"));
        }

        @Test
        @DisplayName("Checking that product's public endpoint is available to everyone and returns the right conteng")
        void testGetProductsPublicInfo() throws Exception {
            mockMvc.perform(get("/products//public/list"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Apple, Banana, Mango, Orange"));
        }
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /employees/user/info")
    class UserInfoTests {

        @Test
        @DisplayName("Когда пользователь авторизован с ролью USER, возвращается статус 200 и нужный контент")
        @WithMockUser(username = "testUser", roles = {"USER"})
        void testGetUserInfoAsUser() throws Exception {
            mockMvc.perform(get("/employees/user/info"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("User Info, secured user information"));
        }

        @Test
        @DisplayName("Когда пользователь не авторизован, ")
        void testGetUserInfoAsAnonymous() throws Exception {
            mockMvc.perform(get("/employees/user/info"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("Tests for endpoint /products/customer/cart")
    class CustomerCartTests {
        @Test
        @DisplayName("When a customer is authorised with the role CUSTOMER, returns status 200 and requariement content")
        @WithMockUser(username = "testCustomer", roles = {"CUSTOMER"})
        void testGetCustomerCartAsCustomer() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Apple: 3 kg, Banana: 2 kg"));
        }

        @Test
        @DisplayName("When a user isn't authorized")
        void testGetCustomerCartAnonymous() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /employees/admin/info")
    class AdminInfoTests {

        @Test
        @DisplayName("Когда пользователь авторизован с ролью ADMIN, возвращается статус 200 и нужный контент")
        @WithMockUser(username = "adminUser", roles = {"ADMIN"})
        void testGetAdminInfoAsAdmin() throws Exception {
            mockMvc.perform(get("/employees/admin/info"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Admin Info, secured admin information"));
        }

        @Test
        @DisplayName("Когда пользователь не имеет роли ADMIN, возвращается ошибка 403")
        @WithMockUser(username = "testUser", roles = {"USER"})
        void testGetAdminInfoAsUser() throws Exception {
            mockMvc.perform(get("/employees/admin/info"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Когда пользователь не авторизован, возвращается ошибка 401 (или 403)")
        void testGetAdminInfoAsAnonymous() throws Exception {
            mockMvc.perform(get("/employees/admin/info"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("Tests for an endpoint /products/manager/add")
    class ManagerAddTests {

        @Test
        @DisplayName("When a manager is authorized, returns status 200 and the requairement content")
        @WithMockUser(username = "testManager", roles = {"MANAGER"})
        void addProductToTheStoreAsManager() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("What product do you want to add to the store?"));
        }

        @Test
        @DisplayName("When the user doesn't have a role admin, returns error 403")
        @WithMockUser(username = "testCustomer", roles = {"CUSTOMER"})
        void addProductToTheStoreAsCustomer() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("When the user isn't authorized, returns error 401(or 403)")
        void addProductToTheStoreAsAnonymous() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().is3xxRedirection());
        }
    }
}