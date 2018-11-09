package DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Event;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class EventDAOTest {
    private Person person;
    private User user;
    private Database db;
    private EventDAO eventDAO;

    @Before
    public void setUp() throws Exception {
        try {
            db = Database.instance();
            eventDAO = db.getEventDAO();
            user = new User("UName", "pword", "fname", "lname",
                    "mail", 'm', "personID");
            person = new Person(user.getUserName(), "fname", "lname", "m");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            db.commit(false);
            db = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    private User user;
    private Person person;
    private Connection connection;
    private EventDAO eventDAO;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMapServer.db";

            connection = DriverManager.getConnection(CONNECTION_URL);

            connection.setAutoCommit(false);

            eventDAO = new EventDAO(connection);

            user = new User("UName", "pword", "fname", "lname",
                    "mail", 'm', "personID");
            person = new Person(user.getUserName(), "fname", "lname", "m");
        }
        catch (SQLException e) {
            throw new DatabaseException("openConnectionFailed");
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            if (connection != null) {
                connection.rollback();
                connection.close();
                connection = null;
            }
            eventDAO = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed");
        }
    }*/

    @Test
    public void createRead() {
        Event event = null;
        event = new Event(user.getUserName(), person.getID(), 0, 1,
                "USA", "DFW", "t",2000);
        event.setID("ID");
        assertNotEquals(null, event);
        eventDAO.create(event);
        Event event2 = eventDAO.read("ID");
        assertEquals(event, event2);
    }

    @Test
    public void read1() {
        ArrayList<Event> events = fill();
        assertTrue(events.size() == 50);

        ArrayList<Event> events2 = eventDAO.read(user);

        assertEquals(events, events2);
    }

    @Test
    public void read2() {
        ArrayList<Event> events = fill();
        assertTrue(events.size() == 50);

        ArrayList<Event> events2 = eventDAO.read(person);

        assertEquals(events, events2);
    }

    @Test
    public void destroy() {
        fill();
        empty();
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Event> events2 = eventDAO.read(user);
        assertEquals(events, events2);
    }

    @Test
    public void odd() {
        Event event = new Event(user.getUserName(), person.getID(), 1, 1,
                "USA", "DFW", "t", 2000);
        eventDAO.destroy(event);  // shouldn't crash...
        Event event2 = eventDAO.read(event.getID());
        assertNull(event2);
    }

    public ArrayList<Event> fill() {
        Event event = null;
        ArrayList<Event> events = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            event = new Event(user.getUserName(), person.getID(), i, i, "USA", "DFW",
                    "t",2000 + i);
            event.setID("ID" + Integer.toString(i));
            events.add(event);
            eventDAO.create(event);
            }
        return events;
    }

    public void empty() {
        ArrayList<Event> events = eventDAO.read(user);
        for (Event event: events) {
            eventDAO.destroy(event);
        }
    }
}