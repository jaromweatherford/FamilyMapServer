package ResponseObjects;

import java.util.ArrayList;

import Model.Event;

/** EventsResponse holds response information to be returned after multi-event requests
 *
 * @author jarom
 * @version 0.0
 */
public class EventsResponse {
    private Event[] events;

    public EventsResponse(ArrayList<Event> events) {
        this.events = new Event[events.size()];
        for (int i = 0; i < events.size(); ++i) {
            this.events[i] = events.get(i);
        }
    }

    public Event[] getEvents() {
        return events;
    }

    public int size() {
        return events.length;
    }
}
