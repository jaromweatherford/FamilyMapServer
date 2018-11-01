package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        if (connection == null) {
            return false;
        }
        String sql = "INSERT INTO Persons(PersonID, DescendantID, FirstName, LastName, Gender, " +
                "FatherID, MotherID, SpouseID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getID());
            statement.setString(2, person.getDescendant());
            statement.setString(3, person.getFirstName());
            statement.setString(4, person.getLastName());
            statement.setString(5, Character.toString(person.getGender()));
            statement.setString(6, (person.getFatherID() == null ? "NULL" : person.getFatherID()));
            statement.setString(7, (person.getMotherID() == null ? "NULL" : person.getMotherID()));
            statement.setString(8, (person.getSpouseID() == null ? "NULL" : person.getSpouseID()));
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** read takes a UUID and returns a person with that UUID as its ID, or NULL if it doesn't exist
     *
     * @param ID                The ID of the person to be returned
     * @return                  The person with that ID, or NULL if no person has that ID
     */
    public Person read(String ID) {
        String sql = "SELECT * FROM Persons WHERE PersonID = ?";
        Person person = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                person = new Person();
                readPerson(rs, person);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return person;
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
        String sql = "DELETE FROM Persons WHERE PersonID = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, person.getID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void readPerson(ResultSet rs, Person person) {
        try {
            person.setID(rs.getString("PersonID"));
            person.setDescendant(rs.getString("DescendantID"));
            person.setFirstName(rs.getString("FirstName"));
            person.setLastName(rs.getString("LastName"));
            person.setGender(rs.getString("Gender").charAt(0));
            person.setFatherID(rs.getString("FatherID"));
            if (person.getFatherID().toUpperCase().equals("NULL")) {
                person.setFatherID(null);
            }
            person.setMotherID(rs.getString("MotherID"));
            if (person.getMotherID().toUpperCase().equals("NULL")) {
                person.setMotherID(null);
            }
            person.setSpouseID(rs.getString("SpouseID"));
            if (person.getSpouseID().toUpperCase().equals("NULL")) {
                person.setSpouseID(null);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
