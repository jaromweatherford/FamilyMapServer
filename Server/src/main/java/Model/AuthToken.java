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

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        AuthToken rhs = (AuthToken) o;
        return (userName.equals(rhs.userName) && code.equals(rhs.code));
    }
}
