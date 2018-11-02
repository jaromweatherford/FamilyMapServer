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
            db = new Database();
            db.openConnection();
            UserDAO userDAO = db.getUserDAO();
            PersonDAO personDAO = db.getPersonDAO();
            TokenDAO tokenDAO = db.getTokenDAO();
            EventDAO eventDAO = db.getEventDAO();
            List<User> userList = userDAO.read();
            List<Person> personList = new ArrayList<>();
            List<AuthToken> tokenList = new ArrayList<>();
            List<Event> eventList = new ArrayList<>();
            System.out.println("1");
            for (User user: userList) {
                System.out.println("2");
                personList.addAll(personDAO.read(user));
                System.out.println("3");
                tokenList.addAll(tokenDAO.read(user));
                System.out.println("4");
                eventList.addAll(eventDAO.read(user));
                System.out.println("5");
            }
            System.out.println("6");
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
            db.closeConnection(true);
            db = null;
            return new MessageResponse("Successfully cleared");
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Internal server errors prevented clearing");
        }
        finally {
            if (db != null) {
                try {
                    db.closeConnection(false);
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
