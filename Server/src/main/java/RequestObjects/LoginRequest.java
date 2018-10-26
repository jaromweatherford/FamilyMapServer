package RequestObjects;

/** LoginRequest holds information regarding a client's login request
 *
 * @author jarom
 * @version 0.0
 */
public class LoginRequest {

    private String userName;
    private String password;


    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
