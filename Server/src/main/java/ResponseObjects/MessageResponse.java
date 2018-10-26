package ResponseObjects;

/** MessageResponse holds message information to be returned after requests
 *
 * @author jarom
 * @version 0.0
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
