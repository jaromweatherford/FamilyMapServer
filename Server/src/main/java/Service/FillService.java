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
                if (!person.getID().equals(user.getPersonID())) {
                    personDAO.destroy(person);
                }
                else {
                    currentGen.add(person);
                }
            }
            for (Event event: events) {
                if (!event.getPerson().equals(user.getUserName())) {
                    eventDAO.destroy(event);
                }
            }

            int peopleAdded = 0;
            int eventsAdded = 0;
            for (int i = 0; i < fillRequest.getGenerations(); ++i) {
                List<Person> nextGen = new ArrayList<>();
                for (Person person: currentGen) {
                    List<Person> parents = createParents(person, user, eventDAO, personDAO);
                    peopleAdded += 2;
                    eventsAdded += 8;
                    nextGen.addAll(parents);
                }
                currentGen = nextGen;
            }

            db.closeConnection(true);
            db = null;
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
            String gender = (p == 0 ? "m" : "f");
            Person parent = new Person(user.getUserName(), firstName, lastName, gender);
            if (p == 0) {
                person.setFatherID(parent.getID());
            }
            else {
                person.setMotherID(parent.getID());
            }
            result.add(parent);
        }
        result.get(0).setSpouseID(result.get(1).getID());
        result.get(1).setSpouseID(result.get(0).getID());
        personDAO.destroy(person);
        personDAO.create(person);
        personDAO.create(result.get(0));
        personDAO.create(result.get(1));
        createEvents(result.get(0), user, eventDAO);
        createEvents(result.get(1), user, eventDAO);
        return result;
    }

    private void createEvents(Person person, User user, EventDAO eventDAO) {
        for (int e = 0; e < 4; ++e) {
            String type;
            switch (e) {
                case(0):
                    type = "BIRTH";
                    break;
                case(1):
                    type = "BAPTISM";
                    break;
                case(3):
                    type = "DEATH";
                    break;
                default:
                    type = "MARRIAGE";
            }
            Event event = new Event(user.getUserName(), person.getID(), 0,
                    0, "", "", type, 0);
            eventDAO.create(event);
        }
    }
}