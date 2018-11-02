package RequestObjects;

import java.util.UUID;

/** EventRequest holds information regarding a client's event request
 *
 * @author jarom
 * @version 0.0
 */
public class EventRequest {
    private String eventID;

    public EventRequest(String eventID) {
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
