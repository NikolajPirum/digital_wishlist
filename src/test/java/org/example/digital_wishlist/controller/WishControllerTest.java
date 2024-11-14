package org.example.digital_wishlist.controller;

import org.example.digital_wishlist.repository.WishRepository;
import org.example.digital_wishlist.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(WishController.class)
    class WishControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private WishService wishService;

        @MockBean
        private WishRepository wishRepository;


        @Test
        void overviewNoUserid() throws Exception {
            mockMvc.perform(get("/overview"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/login"));
        }
        @Test
        void overview() throws Exception {
            mockMvc.perform(get("/overview")
                    .sessionAttr("userId", 1))
                    .andExpect(status().isOk())
                    .andExpect(view().name("wishListSite"));
        }

        @Test
        void createUser() throws Exception {
            mockMvc.perform(get("/create_user"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("create_user"));
        }

        @Test
        void logout() throws Exception {
            mockMvc.perform(get("/logout")
                            .sessionAttr("userId", 1))
                            .andExpect(status().is3xxRedirection())
                            .andExpect(view().name("redirect:/login"));

        }
    }


