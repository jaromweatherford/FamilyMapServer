package ResponseObjects;

import java.util.UUID;

import Model.AuthToken;

/** LoginResponse holds response information to be returned after login requests
 *
 * @author jarom
 * @version 0.0
 */
public class LoginResponse {
    private String authToken;
    private String userName;
    private String personID;

    public LoginResponse(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }
}
