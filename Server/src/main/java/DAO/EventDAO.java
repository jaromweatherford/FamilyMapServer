package DAO;

import Model.Event;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        if (connection == null) {
            return false;
        }
        String sql = "INSERT INTO Events(EventID, DescendantID, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, event.getID());
            statement.setString(2, event.getDescendant());
            statement.setString(3, event.getPerson());
            statement.setDouble(4, event.getLatitude());
            statement.setDouble(5, event.getLongitude());
            statement.setString(6, event.getCountry());
            statement.setString(7, event.getCity());
            statement.setString(8, event.getType().toString());
            statement.setInt(9, event.getYear());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** read takes a UUID and returns an event with that UUID as its ID, or NULL if it doesn't exist
     *
     * @param ID                The ID of the event to be returned
     * @return                  The event with that ID, or NULL if no event has that ID
     */
    public Event read(String ID) {
        String sql = "SELECT * FROM Events WHERE EventID = ?";
        Event event = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                event = new Event();
                readEvent(rs, event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return event;
    }

    /** read takes a person and returns all events related to that person, the list will be empty
     * if the person is not found or if no events are found for them
     *
     * @param person             The person who's events are desired
     * @return
     */
    public ArrayList<Event> read(Person person) {
        String sql = "SELECT * FROM Events WHERE PersonID = ?";
        ArrayList<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                readEvent(rs, event);
                events.add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return events;
    }

    /** read takes a user and returns all events related to that user, the list will be empty
     * if the user is not found or if no events are found for them
     *
     * @param user              The user who's events are desired
     * @return                  The event with that ID, or NULL if no event has that ID
     */
    public ArrayList<Event> read(User user) {
        String sql = "SELECT * FROM Events WHERE DescendantID = ?";
        ArrayList<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                readEvent(rs, event);
                events.add(event);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return events;
    }

    /** destroy takes an event and removes it from the database
     *
     * @param event             The event to be removed
     * @return                  false if the deletion failed, true if it succeeded
     */
    public boolean destroy(Event event) {
        String sql = "DELETE FROM Events WHERE EventID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, event.getID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void readEvent(ResultSet rs, Event event) {
        try {
            event.setID(rs.getString("EventID"));
            event.setDescendant(rs.getString("DescendantID"));
            event.setPerson(rs.getString("PersonID"));
            event.setLatitude(rs.getDouble("Latitude"));
            event.setLongitude(rs.getDouble("Longitude"));
            event.setCountry(rs.getString("Country"));
            event.setCity(rs.getString("City"));
            event.setType(rs.getString("EventType"));
            event.setYear(rs.getInt("Year"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
