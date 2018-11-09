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

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        MessageResponse rhs = (MessageResponse) o;
        if (message == null) {
            return rhs.message == null;
        }
        return message.equals(rhs.message);
    }
}
