package org.example.digital_wishlist.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllWishLists() {
        int expectedResult = 3;
        int actualResult = wishRepository.getAllWishLists().size();
        assertEquals(expectedResult, actualResult);
    }

}