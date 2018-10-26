package ResponseObjects;

import Model.User;

/** RegisterResponse holds response information to be returned after registration requests
 *
 * @author jarom
 * @version 0.0
 */
public class RegisterResponse {
    private User user;

    public RegisterResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
