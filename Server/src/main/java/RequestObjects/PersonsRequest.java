package RequestObjects;

/** PersonsRequest holds information regarding a client's persons request
 *
 * @author jarom
 * @version 0.0
 */
public class PersonsRequest {
    private String tokenCode;

    public PersonsRequest(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }
}
