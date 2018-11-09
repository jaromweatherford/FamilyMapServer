package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.Database;
import DAO.DatabaseException;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.Event;
import Model.Person;
import Model.User;
import RequestObjects.LoadRequest;
import ResponseObjects.MessageResponse;

/** LoadService handles the model manipulation and database calls for loading operations
 *
 * @author jarom
 * @version 0.0
 */
public class LoadService {
    /** run takes a loadRequest, clears the database, fills it with the data in the loadRequest,
     * and returns a success message.
     *
     * @param loadRequest               The data to be loaded into the database
     * @return                          A message detailing the work done
     * @throws InvalidInputException
     */
    public MessageResponse run(LoadRequest loadRequest) throws InternalServerErrorException {
        Database db = null;
        MessageResponse response = new MessageResponse("Failed to load");
        try {
            new ClearService().run();

            db = Database.instance();

            UserDAO userDAO = db.getUserDAO();
            PersonDAO personDAO = db.getPersonDAO();
            EventDAO eventDAO = db.getEventDAO();

            List<User> users = new ArrayList<>();
            for (User user: loadRequest.getUsers()) {
                users.add(user);
            }
            List<Person> persons = new ArrayList<>();
            for (Person person: loadRequest.getPersons()) {
                persons.add(person);
            }
            List<Event> events = new ArrayList<>();
            for (Event event: loadRequest.getEvents()) {
                events.add(event);
            }

            for (User user : users) {
                userDAO.create(user);
            }
            for (Person person : persons) {
                personDAO.create(person);
            }
            for (Event event : events) {
                eventDAO.create(event);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Successfully loaded ");
            sb.append(users.size());
            sb.append(" users, ");
            sb.append(persons.size());
            sb.append(" persons, and ");
            sb.append(events.size());
            sb.append(" events to the database");
            response = new MessageResponse(sb.toString());
            db.commit(true);
            db = null;
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
        return response;
    }
}
