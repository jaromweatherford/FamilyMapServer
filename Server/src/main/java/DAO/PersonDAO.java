package DAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

import Model.Event;
import Model.Person;
import Model.User;

/** PersonDAO allows and handles database access for Person objects
 *
 * @author jarom
 * @version 0.0
 */
public class PersonDAO {
    Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    /** create adds the passed person to the database, also adds its relations to the database
     *
     * @param person            The token to be added to the database
     * @return                  false if the update failed, true if it succeeded
     */
    public boolean create(Person person) {
        return false;
    }

    /** read takes a UUID and returns a person with that UUID as its ID, or NULL if it doesn't exist
     *
     * @param ID                The ID of the person to be returned
     * @return                  The person with that ID, or NULL if no person has that ID
     */
    public Person read(UUID ID) {
        return null;
    }

    /** read takes a user and returns all people related to that user, the list will be empty
     * if the user is not found or if no people are found for them
     *
     * @param user              The user who's tokens are desired
     * @return                  A list of all people found to relate to that user
     */
    public ArrayList<Person> read(User user) {
        return new ArrayList<>();
    }

    /** destroy takes a person and removes it from the database
     *
     * @param person            The person to be removed
     * @return                  false if the deletion failed, true if it succeeded
     */
    public boolean destroy(Person person) {
        return false;
    }
}
