package ResponseObjects;

import Model.Person;

/** PersonResponse holds response information to be returned after person requests
 *
 * @author jarom
 * @version 0.0
 */
public class PersonResponse extends Person {

    public PersonResponse(Person person) {
        setID(person.getID());
        setDescendant(person.getDescendant());
        setFirstName(person.getFirstName());
        setLastName(person.getLastName());
        setGender(person.getGender());
        setFatherID(person.getFatherID());
        setMotherID(person.getMotherID());
        setSpouseID(person.getSpouseID());
    }
}
