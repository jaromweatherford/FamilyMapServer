package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import DAO.Database;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.Event;
import Model.Person;
import Model.User;
import RequestObjects.LoadRequest;

import static org.junit.Assert.*;

public class LoadServiceTest {
    private TestDB tdb;
    private LoadService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        service = new LoadService();
    }

    @After
    public void tearDown() throws Exception {
        tdb.dePopulate();
    }

    @Test
    public void run() {
        Database db = null;
        try {
            db = Database.instance();

            UserDAO userDAO = db.getUserDAO();
            PersonDAO personDAO = db.getPersonDAO();
            EventDAO eventDAO = db.getEventDAO();

            assertEquals(new ArrayList<User>(), userDAO.read());

            User[] users = new User[1];
            users[0] = tdb.getU1();
            Person[] persons = new Person[1];
            persons[0] = tdb.getP1();
            Event[] events = new Event[1];
            events[0] = tdb.getE1();

            LoadRequest request = new LoadRequest(users, persons, events);
            assertEquals("Successfully loaded 1 users, 1 persons, and 1 events to the database",
                    service.run(request).getMessage());

            assertTrue(1 == userDAO.read().size());

            ArrayList<User> uray = new ArrayList<>();
            uray.add(tdb.getU1());
            ArrayList<Person> pray = new ArrayList<>();
            pray.add(tdb.getP1());
            ArrayList<Event> eray = new ArrayList<>();
            eray.add(tdb.getE1());

            assertEquals(uray, userDAO.read());
            assertEquals(pray, personDAO.read(tdb.getU1()));
            assertEquals(eray, eventDAO.read(tdb.getU1()));

            users[0] = tdb.getU2();
            persons[0] = tdb.getP2();
            events[0] = tdb.getE2();

            request = new LoadRequest(users, persons, events);
            assertEquals("Successfully loaded 1 users, 1 persons, and 1 events to the database",
                    service.run(request).getMessage());

            assertTrue(1 == userDAO.read().size());

            uray.clear();
            uray.add(tdb.getU2());
            pray.clear();
            pray.add(tdb.getP2());
            eray.clear();
            eray.add(tdb.getE2());

            assertEquals(uray, userDAO.read());
            assertEquals(pray, personDAO.read(tdb.getU2()));
            assertEquals(eray, eventDAO.read(tdb.getU2()));

            assertEquals(new ArrayList<Person>(), personDAO.read(tdb.getU1()));
            assertEquals(new ArrayList<Event>(), eventDAO.read(tdb.getU1()));

            db.commit(false);
            db = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null) {
                try {
                    db.commit(false);
                    db = null;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}