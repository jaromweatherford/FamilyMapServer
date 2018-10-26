package RequestObjects;

import java.util.UUID;

/** EventRequest holds information regarding a client's event request
 *
 * @author jarom
 * @version 0.0
 */
public class EventRequest {
    private UUID eventID;
    private String tokenCode;

    public EventRequest(UUID eventID) {
        this.eventID = eventID;
    }

    public UUID getEventID() {
        return eventID;
    }

    public void setEventID(UUID eventID) {
        this.eventID = eventID;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }
}
