package ResponseObjects;

import java.util.ArrayList;

import Model.Event;

/** EventsResponse holds response information to be returned after multi-event requests
 *
 * @author jarom
 * @version 0.0
 */
public class EventsResponse {
    private ArrayList<Event> events;

    public EventsResponse(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
