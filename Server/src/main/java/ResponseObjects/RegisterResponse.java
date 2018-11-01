package ResponseObjects;

import Model.User;

/** RegisterResponse holds response information to be returned after registration requests
 *
 * @author jarom
 * @version 0.0
 */
public class RegisterResponse {
    private String authToken;
    private String userName;
    private String personID;

    public RegisterResponse(String authToken, String userName, String personID) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public RegisterResponse() {
        this.authToken = "";
        this.userName = "";
        this.personID = "";
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
