package ResponseObjects;

import Model.Event;

/** EventResponse holds response information to be returned after event requests
 *
 * @author jarom
 * @version 0.0
 */
public class EventResponse extends Event {

    public EventResponse(Event event) {
        setID(event.getID());
        setDescendant(event.getDescendant());
        setPerson(event.getPerson());
        setLatitude(event.getLatitude());
        setLongitude(event.getLongitude());
        setCountry(event.getCountry());
        setCity(event.getCity());
        setType(event.getType());
        setYear(event.getYear());
    }
}
