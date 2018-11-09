package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.EventDAO;
import DAO.TokenDAO;
import Model.AuthToken;
import Model.Event;
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
     * @param eventRequest      The request information
     * @return                  The event with the passed ID
     * @throws Service.EventNotFoundException
     */
    public EventResponse run(EventRequest eventRequest) throws EventNotFoundException,
                                                                InternalServerErrorException {
        Database db = null;
        try {
            db = Database.instance();

            TokenDAO tokenDAO = db.getTokenDAO();
            AuthToken token = tokenDAO.read(eventRequest.getAuthToken());
            EventDAO eventDAO = db.getEventDAO();
            Event event = eventDAO.read(eventRequest.getEventID());
            if (event == null) {
                throw new EventNotFoundException();
            }
            if (token == null) {
                throw new InternalServerErrorException("Couldn't find the token");
            }
            if (!token.getUserName().equals(event.getDescendant())) {
                throw new EventNotFoundException();
            }
            EventResponse eventResponse = new EventResponse(event);

            db.commit(true);
            db = null;
            return eventResponse;
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Database failed");
        }
        finally {
            if (db != null) {
                try {
                    db.commit(false);
                    db = null;
                }
                catch (DatabaseException e) {
                    System.out.println("FAILED TO CLOSE CONNECTION");
                    throw new InternalServerErrorException("FAILED TO CLOSE CONNECTION!");
                }
            }
        }
    }
}
