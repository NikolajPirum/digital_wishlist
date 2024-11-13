package org.example.digital_wishlist.repository;

import org.example.digital_wishlist.model.Wishlist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// Uses active profile to make sure we are hooked to database
@ActiveProfiles("h2")
// @SQL ensures that h2 is reset for usage
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2db.sql")
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

    @Test
    void getWishlist() {
        int wishListId = 1;
        Wishlist wishlist = wishRepository.getWishlist(wishListId);
        assertNotNull(wishlist);

    }
    @Test
    void updateWishlist() {
        int wishListId = 1;
        Wishlist wishlist = wishRepository.getWishlist(wishListId);
        System.out.println("WISHLIST: " + wishlist);
        assertNotNull(wishlist);
        int expectedResult = 1;
        int actualResult = wishRepository.updateWishlist(wishlist);
        assertEquals(expectedResult, actualResult);
    }





}