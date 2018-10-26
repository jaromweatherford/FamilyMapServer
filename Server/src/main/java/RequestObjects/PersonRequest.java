package RequestObjects;

import java.util.UUID;

/** PersonRequest holds information regarding a client's person request
 *
 * @author jarom
 * @version 0.0
 */
public class PersonRequest {
    private UUID personID;
    private String tokenCode;

    public PersonRequest(UUID personID) {
        this.personID = personID;
    }

    public UUID getPersonID() {
        return personID;
    }

    public void setPersonID(UUID personID) {
        this.personID = personID;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }
}
