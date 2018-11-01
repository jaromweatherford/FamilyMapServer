package Model;

import java.security.SecureRandom;

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

        //Thanks to Daniel de Zwaan: https://stackoverflow.com/questions/13992972/how-to-create-a-authentication-token-using-java
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        this.code = bytes.toString();
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
