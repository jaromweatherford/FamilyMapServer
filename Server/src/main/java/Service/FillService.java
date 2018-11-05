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
                personDAO.destroy(person);
            }
            for (Event event: events) {
                if (!event.getPerson().equals(user.getUserName())) {
                    eventDAO.destroy(event);
                }
            }

            Person root = new Person(user.getUserName(), user.getFirstName(), user.getLastName(),
                    Character.toString(user.getGender()));
            root.setID(user.getUserName());
            currentGen.add(root);
            personDAO.create(root);

            user.setPersonID(root.getID());
            userDAO.destroy(user);
            userDAO.create(user);

            int birthYear = 1994;
            createEvents(root, birthYear, user, eventDAO, false);

            int peopleAdded = 1;
            int eventsAdded = 3;
            for (int i = 0; i < fillRequest.getGenerations(); ++i) {
                List<Person> nextGen = new ArrayList<>();
                for (Person person: currentGen) {
                    List<Person> parents = createParents(person, birthYear, user, eventDAO, personDAO);
                    peopleAdded += 2;
                    eventsAdded += 8;
                    nextGen.addAll(parents);
                }
                currentGen = nextGen;
                birthYear -= 35;
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

    private List<Person> createParents(Person person, int birthYear, User user, EventDAO eventDAO,
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
        createEvents(result.get(0), birthYear - 30, user, eventDAO, true);
        createEvents(result.get(1), birthYear - 30, user, eventDAO, true);
        return result;
    }

    private void createEvents(Person person, int birthYear, User user, EventDAO eventDAO, boolean dead) {
        int random = (int)Math.floor(9 * Math.random());
        for (int e = 0; e < 4; ++e) {
            int year = birthYear;
            String type;
            switch (e) {
                case(0):
                    type = "BIRTH";
                    year += random;
                    break;
                case(1):
                    type = "BAPTISM";
                    if (Math.random() < 0.9) {
                        year += 8 + random;
                    }
                    else {
                        year += (32 * Math.random()) + 40 + random;
                    }
                    break;
                case(3):
                    type = "DEATH";
                    year += (20 * Math.random()) + 80;
                    break;
                default:
                    type = "MARRIAGE";
                    year += 26;
            }
            if (year > 2018) {
                year = 2018;
            }
            Event event = new Event(user.getUserName(), person.getID(), 40.7128,
                    74.0060, "USA", "New York", type, year);
            if (dead || !type.equals("DEATH")) {
                eventDAO.create(event);
            }
        }
    }
}