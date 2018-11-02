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
import RequestObjects.FillRequest;
import ResponseObjects.MessageResponse;

/** FillService handles the model manipulation and database calls for fill operations
 *
 * @author jarom
 * @version 0.0
 */
public class FillService {
    /** run takes a user and a number of generations to generate, clears any previous data
     * for that user, and generates randomized ancestor data to the given number of generations
     *
     * @param fillRequest               The information needed to handle the fill
     * @return                          A message describing the data generated
     * @throws UserNotFoundException
     */
    public MessageResponse run(FillRequest fillRequest) throws UserNotFoundException,
                                                        InvalidInputException,
                                                        InternalServerErrorException {
        if (fillRequest.getGenerations() < 0) {
            throw new InvalidInputException();
        }
        System.out.println(fillRequest.getGenerations());
        Database db = null;
        try {
            db = new Database();
            db.openConnection();

            UserDAO userDAO = db.getUserDAO();
            User user = userDAO.read(fillRequest.getUser());
            if (user == null) {
                throw new UserNotFoundException();
            }

            PersonDAO personDAO = db.getPersonDAO();
            EventDAO eventDAO = db.getEventDAO();

            List<Person> persons = personDAO.read(user);
            List<Event> events = eventDAO.read(user);

            List<Person> currentGen = new ArrayList<>();

            for (Person person: persons) {
                if (!person.getID().equals(user.getUsername())) {
                    personDAO.destroy(person);
                }
                else {
                    currentGen.add(person);
                }
            }
            for (Event event: events) {
                if (!event.getPerson().equals(user.getUsername())) {
                    eventDAO.destroy(event);
                }
            }

            for (int i = 0; i < fillRequest.getGenerations(); ++i) {
                List<Person> nextGen = new ArrayList<>();
                for (Person person: currentGen) {
                    for (int p = 0; p < 2; ++p) {
                        nextGen.addAll(createParents(person, user, eventDAO, personDAO));
                    }
                }
            }

            db.closeConnection(true);
            db = null;
            int peopleAdded = (int)Math.pow(2, fillRequest.getGenerations() + 1) - 2;
            int eventsAdded = peopleAdded * 4;
            String message = "Successfully added " + peopleAdded + " people and "
                    + eventsAdded + " events to the database";
            return new MessageResponse(message);
        }
        catch (DatabaseException e) {
            try {
                if (db != null) {
                    db.closeConnection(false);
                    db = null;
                    return new MessageResponse("Failed to fill the database");
                }
            }
            catch (DatabaseException de) {
                throw new InternalServerErrorException("FAILED TO CLOSE CONNECTION!");
            }
        }
        return new MessageResponse("If you can read this, something really weird happened");
    }

    private List<Person> createParents(Person person, User user, EventDAO eventDAO,
                                       PersonDAO personDAO) throws InvalidInputException {
        List<Person> result = new ArrayList<>();
        for (int p = 0; p < 2; ++p) {
            String firstName = (p == 0 ? "Father" : "Mother");
            String lastName = "McPerson";
            char gender = (p == 0 ? 'm' : 'f');
            Person parent = new Person(user.getUsername(), firstName, lastName, gender);
            personDAO.create(parent);
            result.add(parent);
            createEvents(parent, user, eventDAO);
            if (p == 0) {
                person.setFatherID(parent.getID());
            }
            else {
                person.setMotherID(parent.getID());
            }
        }
        return result;
    }

    private void createEvents(Person person, User user, EventDAO eventDAO) {
        for (int e = 0; e < 4; ++e) {
            Event.EventType type;
            switch (e) {
                case(0):
                    type = Event.EventType.BIRTH;
                    break;
                case(1):
                    type = Event.EventType.BAPTISM;
                    break;
                case(3):
                    type = Event.EventType.DEATH;
                    break;
                default:
                    type = Event.EventType.MARRIAGE;
            }
            Event event = new Event(user.getUsername(), person.getID(), 0,
                    0, "", "", type, 0);
            eventDAO.create(event);
        }
    }
}