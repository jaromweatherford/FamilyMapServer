package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.EventDAO;
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
            db = new Database();
            db.openConnection();
            EventDAO eventDAO = db.getEventDAO();
            Event event = eventDAO.read(eventRequest.getEventID());
            if (event == null) {
                throw new EventNotFoundException();
            }
            EventResponse eventResponse = new EventResponse(event);
            db.closeConnection(true);
            db = null;
            return eventResponse;
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            try {
                if (db != null) {
                    db.closeConnection(false);
                    db = null;
                }
            }
            catch (DatabaseException de) {
                de.printStackTrace();
                throw new InternalServerErrorException("FAILED TO CLOSE CONNECTION!");
            }
            throw new InternalServerErrorException("Database failed");
        }
    }
}
