package presentation.General;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserCancelledException
 * Tests exception creation and messages
 */
public class UserCancelledExceptionTest {
    
    @Test
    @DisplayName("Default constructor - Should have default message")
    void testDefaultConstructor_ShouldHaveDefaultMessage() {
        UserCancelledException exception = new UserCancelledException();
        
        assertEquals("Operation cancelled by user", exception.getMessage());
    }
    
    @Test
    @DisplayName("Constructor with message - Should have custom message")
    void testConstructorWithMessage_ShouldHaveCustomMessage() {
        UserCancelledException exception = new UserCancelledException("Custom cancellation message");
        
        assertEquals("Custom cancellation message", exception.getMessage());
    }
    
    @Test
    @DisplayName("Should be throwable")
    void testShouldBeThrowable() {
        assertThrows(UserCancelledException.class, () -> {
            throw new UserCancelledException();
        });
    }
    
    @Test
    @DisplayName("Should be instance of RuntimeException")
    void testShouldBeInstanceOfRuntimeException() {
        UserCancelledException exception = new UserCancelledException();
        
        assertTrue(exception instanceof RuntimeException);
    }
    
    @Test
    @DisplayName("getMessage - Null message - Should handle gracefully")
    void testGetMessage_NullMessage_ShouldHandleGracefully() {
        UserCancelledException exception = new UserCancelledException(null);
        
        assertNull(exception.getMessage());
    }
    
    @Test
    @DisplayName("Can be caught as RuntimeException")
    void testCanBeCaughtAsRuntimeException() {
        try {
            throw new UserCancelledException("Test");
        } catch (RuntimeException e) {
            assertTrue(e instanceof UserCancelledException);
            assertEquals("Test", e.getMessage());
        }
    }
}

