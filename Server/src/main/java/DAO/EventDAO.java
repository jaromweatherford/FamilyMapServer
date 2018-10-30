package DAO;

import Model.Event;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

/** EventDAO allows and handles database access for Event objects
 *
 * @author jarom
 * @version 0.0
 */
public class EventDAO {
    Connection connection;

    public EventDAO(Connection connection) {
        this.connection = connection;
    }

    /** create adds the passed event to the database, also adds its relations to the database
     *
     * @param event             The event to be added to the database
     * @return                  false if the update failed, true if it succeeded
     */
    public boolean create(Event event) {
        return false;
    }

    /** read takes a UUID and returns an event with that UUID as its ID, or NULL if it doesn't exist
     *
     * @param ID                The ID of the event to be returned
     * @return                  The event with that ID, or NULL if no event has that ID
     */
    public Event read(UUID ID) {
        return null;
    }

    /** read takes a person and returns all events related to that person, the list will be empty
     * if the person is not found or if no events are found for them
     *
     * @param person             The person who's events are desired
     * @return
     */
    public ArrayList<Event> read(Person person) {
        return new ArrayList<>();
    }

    /** read takes a user and returns all events related to that user, the list will be empty
     * if the user is not found or if no events are found for them
     *
     * @param user              The user who's events are desired
     * @return                  The event with that ID, or NULL if no event has that ID
     */
    public ArrayList<Event> read(User user) {
        return new ArrayList<>();
    }

    /** destroy takes an event and removes it from the database
     *
     * @param event            The event to be removed
     * @return                  false if the deletion failed, true if it succeeded
     */
    public boolean destroy(Event event) {
        return false;
    }
}
