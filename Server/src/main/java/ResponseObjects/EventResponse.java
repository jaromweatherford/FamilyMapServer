package ResponseObjects;

import Model.Event;

/** EventResponse holds response information to be returned after event requests
 *
 * @author jarom
 * @version 0.0
 */
public class EventResponse {
    private Event event;

    public EventResponse(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
