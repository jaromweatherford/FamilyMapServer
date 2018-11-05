package RequestObjects;

import java.util.ArrayList;
import java.util.List;

import Model.Event;
import Model.Person;
import Model.User;

/** LoadRequest holds information regarding a client's load request
 *
 * @author jarom
 * @version 0.0
 */
public class LoadRequest {
    private User[] users;
    private Person[] persons;
    private Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }

    /*public void setUsers(List<User> users) {
        this.users = users;
    }

    //public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    //public void setEvents(List<Event> events) {
        this.events = events;
    }*/
}
