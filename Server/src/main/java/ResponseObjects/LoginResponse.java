package ResponseObjects;

import java.util.UUID;

import Model.AuthToken;

/** LoginResponse holds response information to be returned after login requests
 *
 * @author jarom
 * @version 0.0
 */
public class LoginResponse {
    private AuthToken authToken;
    private String userName;
    private UUID personID;

    public LoginResponse(AuthToken authToken, String userName, UUID personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public UUID getPersonID() {
        return personID;
    }
}
