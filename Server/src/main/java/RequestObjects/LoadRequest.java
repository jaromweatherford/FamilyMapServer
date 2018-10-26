package RequestObjects;

import java.util.ArrayList;

import Model.Event;
import Model.Person;
import Model.User;

/** LoadRequest holds information regarding a client's load request
 *
 * @author jarom
 * @version 0.0
 */
public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
