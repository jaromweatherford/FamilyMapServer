package ResponseObjects;

import Model.Person;

/** PersonResponse holds response information to be returned after person requests
 *
 * @author jarom
 * @version 0.0
 */
public class PersonResponse {
    private Person person;

    public PersonResponse(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
