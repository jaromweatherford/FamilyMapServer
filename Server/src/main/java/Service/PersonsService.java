package Service;

import java.util.ArrayList;

import Model.Person;
import RequestObjects.PersonsRequest;

/** PersonsService handles the model manipulation and database calls for requesting multiple persons
 *
 * @author jarom
 * @version 0.0
 */
public class PersonsService {
    /** run takes a user and returns the people that relate to that user, throws an error if
     * there is no such user
     *
     * @param user              The user who's people are desired
     * @return                  A list of the people related to the user
     * @throws Service.UserNotFoundException
     */
    public ArrayList<Person> run(PersonsRequest personsRequest) throws UserNotFoundException {
        throw new UserNotFoundException();
    }
}
