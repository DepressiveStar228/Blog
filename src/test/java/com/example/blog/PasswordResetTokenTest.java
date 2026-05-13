package com.example.blog;

import com.example.blog.entity.PasswordResetToken;
import com.example.blog.entity.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PasswordResetTokenTest {
    @Test
    void isExpired_ReturnsTrue_WhenExpirationDateIsInThePast() {
        PasswordResetToken token = new PasswordResetToken();
        token.setExpiresAt(LocalDateTime.now().minusHours(1));

        assertTrue(token.isExpired(), "Токен має бути простроченим");
    }

    @Test
    void isExpired_ReturnsFalse_WhenExpirationDateIsInTheFuture() {
        PasswordResetToken token = new PasswordResetToken();
        token.setExpiresAt(LocalDateTime.now().plusHours(1));

        assertFalse(token.isExpired(), "Токен ще має бути дійсним");
    }

    @Test
    void testGettersAndSetters() {
        PasswordResetToken token = new PasswordResetToken();
        User user = new User();
        user.setEmail("test@test.com");
        LocalDateTime time = LocalDateTime.now();

        token.setId(1L);
        token.setToken("uuid-token");
        token.setUser(user);
        token.setExpiresAt(time);

        assertEquals(1L, token.getId());
        assertEquals("uuid-token", token.getToken());
        assertEquals(user, token.getUser());
        assertEquals(time, token.getExpiresAt());
    }
}
