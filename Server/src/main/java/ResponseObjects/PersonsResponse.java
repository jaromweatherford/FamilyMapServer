package ResponseObjects;

import java.util.ArrayList;
import java.util.List;

import Model.Person;

/** PersonsResponse holds response information to be returned after multi-person requests
 *
 * @author jarom
 * @version 0.0
 */
public class PersonsResponse {
    private Person[] people;

    public PersonsResponse(Person[] people) {
        this.people = people;
    }

    public PersonsResponse(List<Person> people) {
        this.people = new Person[people.size()];
        for (int i = 0; i < people.size(); ++i) {
            this.people[i] = people.get(i);
        }
    }

    public Person[] getPeople() {
        return people;
    }
}
