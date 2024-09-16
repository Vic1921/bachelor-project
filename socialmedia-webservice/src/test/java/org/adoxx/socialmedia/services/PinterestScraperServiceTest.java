package org.adoxx.socialmedia.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PinterestScraperServiceTest {

    @Autowired
    private PinterestScraperService pinterestScraperService;

    @BeforeAll
    void setup() {
        // lazy init
        // pinterestScraperService.setup();
    }

    @AfterAll
    void teardown() {
        pinterestScraperService.teardown();
    }

    /*@Test
    void testLogin() {
        assertTrue(pinterestScraperService.login());
    }*/

    @Test
    void testFetchComments() {
        // pinterestScraperService.login();
        List<String> comments = pinterestScraperService.fetchComments("1124703706933675772");
        assertFalse(comments.isEmpty());
        assertEquals(31, comments.size());
    }

    @Test
    void testPostComment() {
        pinterestScraperService.postComment("1124703706933675772", "Test comment from testPostComment inside the TestClass");
        //assertNotNull(comment);
    }
}