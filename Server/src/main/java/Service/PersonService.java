package Service;

import RequestObjects.PersonRequest;
import ResponseObjects.PersonResponse;

/** PersonService handles the model manipulation and database calls for requesting a person
 *
 * @author jarom
 * @version 0.0
 */
public class PersonService {
    /** run takes a UUID and returns the person that has that ID, throws an error if there is
     * no such person
     *
     * @param ID                The UUID of the desired person
     * @return                  The person with the passed ID
     * @throws Service.PersonNotFoundException
     */
    public PersonResponse run(PersonRequest personRequest) throws PersonNotFoundException {
        throw new PersonNotFoundException();
    }

}
