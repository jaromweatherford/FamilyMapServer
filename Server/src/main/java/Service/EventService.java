package Service;

import RequestObjects.EventRequest;
import ResponseObjects.EventResponse;

/** EventService handles the model manipulation and database calls for requesting an event
 *
 * @author jarom
 * @version 0.0
 */
public class EventService {
    /**
     * run takes a UUID and returns the event that has that ID, throws an error if there is
     * no such event
     * @param ID                The UUID of the desired event
     * @return                  The event with the passed ID
     * @throws Service.EventNotFoundException
     */
    public EventResponse run(EventRequest eventRequest) throws EventNotFoundException {
        throw new EventNotFoundException();
    }

}
