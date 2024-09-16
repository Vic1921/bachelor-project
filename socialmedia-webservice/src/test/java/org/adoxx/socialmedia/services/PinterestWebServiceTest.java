package org.adoxx.socialmedia.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PinterestWebServiceTest {

    @Autowired
    private PinterestWebService pinterestWebService;

    @AfterAll
    void teardown() {
        pinterestWebService.teardown();
    }

    @Test
    void testFetchComments() {
        List<String> comments = pinterestWebService.fetchComments("1124703706933675772");
        assertFalse(comments.isEmpty());
        assertEquals(31, comments.size());
    }

    @Test
    void testPostComment() {
        pinterestWebService.postComment("1124703706933675772", "Test comment from testPostComment inside the TestClass");
    }
}