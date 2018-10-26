package ResponseObjects;

import java.util.ArrayList;

import Model.Person;

/** PersonsResponse holds response information to be returned after multi-person requests
 *
 * @author jarom
 * @version 0.0
 */
public class PersonsResponse {
    private ArrayList<Person> people;

    public PersonsResponse(ArrayList<Person> people) {
        this.people = people;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }
}
