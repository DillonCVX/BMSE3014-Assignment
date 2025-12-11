package presentation.General;

/**
 * Exception thrown when user cancels an operation by entering X
 */
public class UserCancelledException extends RuntimeException {
    
    public UserCancelledException() {
        super("Operation cancelled by user");
    }
    
    public UserCancelledException(String message) {
        super(message);
    }
}

