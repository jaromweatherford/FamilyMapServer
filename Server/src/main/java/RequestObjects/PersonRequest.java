package RequestObjects;

import java.util.UUID;

/** PersonRequest holds information regarding a client's person request
 *
 * @author jarom
 * @version 0.0
 */
public class PersonRequest {
    private String personID;

    public PersonRequest(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
