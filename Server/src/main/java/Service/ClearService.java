package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.Database;
import DAO.DatabaseException;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.Person;
import Model.User;
import Model.AuthToken;
import Model.Event;
import ResponseObjects.MessageResponse;

/** ClearService handles the model manipulation and database calls for clearing operations
 *
 * @author jarom
 * @version 0.0
 */
public class ClearService {

    public ClearService() {
    }

    /**
     * run removes everything from the database. No questions asked, none answered.
     * @throws InternalServerErrorException
     */
    public MessageResponse run()  throws InternalServerErrorException {
        Database db = null;
        try {
            db = Database.instance();

            UserDAO userDAO = db.getUserDAO();
            PersonDAO personDAO = db.getPersonDAO();
            TokenDAO tokenDAO = db.getTokenDAO();
            EventDAO eventDAO = db.getEventDAO();
            List<User> userList = userDAO.read();
            List<Person> personList = new ArrayList<>();
            List<AuthToken> tokenList = new ArrayList<>();
            List<Event> eventList = new ArrayList<>();
            for (User user: userList) {
                personList.addAll(personDAO.read(user));
                tokenList.addAll(tokenDAO.read(user));
                eventList.addAll(eventDAO.read(user));
            }
            for (Event event: eventList) {
                eventDAO.destroy(event);
            }
            for (AuthToken token: tokenList) {
                tokenDAO.destroy(token);
            }
            for (Person person: personList) {
                personDAO.destroy(person);
            }
            for (User user: userList) {
                userDAO.destroy(user);
            }
            db.commit(true);
            db = null;
            return new MessageResponse("Successfully cleared");
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
