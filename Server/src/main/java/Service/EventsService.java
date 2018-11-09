package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.AuthToken;
import Model.User;
import RequestObjects.EventsRequest;
import ResponseObjects.EventsResponse;
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
     * @param eventsRequest     The information regarding the request
     * @return                  A list of the events related to the user
     * @throws Service.UserNotFoundException
     */
    public EventsResponse run(EventsRequest eventsRequest) throws UserNotFoundException,
                                                                    InternalServerErrorException {
        Database db = null;
        try {
            db = Database.instance();

            TokenDAO tokenDAO = db.getTokenDAO();
            AuthToken token = tokenDAO.read(eventsRequest.getTokenCode());
            if (token == null) {
                throw new UserNotFoundException();
            }
            UserDAO userDAO = db.getUserDAO();
            User user = userDAO.read(token.getUserName());
            if (user == null) {
                throw new UserNotFoundException();
            }
            EventDAO eventDAO = db.getEventDAO();
            EventsResponse eventsResponse = new EventsResponse(eventDAO.read(user));

            db.commit(true);
            db = null;
            return eventsResponse;
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
