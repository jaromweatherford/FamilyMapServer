package Model;

import java.util.UUID;

/** AuthToken holds all the information directly relating to a particular authorization token
 *
 * @author jarom
 * @version 0.0
 */
public class AuthToken {
    private String code;
    private String userName;

    public AuthToken(String userName) {
        this.userName = userName;
        this.code = UUID.randomUUID().toString();
    }

    public AuthToken() {
        this.userName = "";
        this.code = "";
    }

    public String getCode() {
        return code;
    }

    public String getUserName() {
        return userName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
