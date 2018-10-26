package Service;

import RequestObjects.EventsRequest;
import ResponseObjects.EventsResponse;

/** EventsService handles the model manipulation and database calls for requesting multiple events
 *
 * @author jarom
 * @version 0.0
 */
public class EventsService {
    /** run takes a user and returns the events that relate to that user, throws an error if
     * there is no such user
     *
     * @param user              The user who's events are desired
     * @return                  A list of the events related to the user
     * @throws Service.UserNotFoundException
     */
    public EventsResponse run(EventsRequest eventsRequest) throws UserNotFoundException {
        throw new UserNotFoundException();
    }
}
