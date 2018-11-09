package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import DAO.Database;
import DAO.DatabaseException;
import DAO.EventDAO;
import DAO.PersonDAO;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

import static org.junit.Assert.*;

public class ClearServiceTest {
    private TestDB tdb;
    private ClearService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();
        service = new ClearService();
    }

    @After
    public void tearDown() throws Exception {
        tdb.dePopulate();
    }

    @Test
    public void run() {
        UserDAO userDAO = null;
        PersonDAO personDAO = null;
        EventDAO eventDAO = null;
        TokenDAO tokenDAO = null;
        Database db = null;
        try {
            db = Database.instance();
            userDAO = db.getUserDAO();
            personDAO = db.getPersonDAO();
            eventDAO = db.getEventDAO();
            tokenDAO = db.getTokenDAO();

            assertEquals("Successfully cleared", service.run().getMessage());

            assertEquals(new ArrayList<User>(), userDAO.read());
            assertEquals(new ArrayList<Person>(), personDAO.read(tdb.getU1()));
            assertEquals(new ArrayList<Event>(), eventDAO.read(tdb.getU1()));
            assertEquals(new ArrayList<AuthToken>(), tokenDAO.read(tdb.getU1()));
            assertEquals(new ArrayList<Person>(), personDAO.read(tdb.getU2()));
            assertEquals(new ArrayList<Event>(), eventDAO.read(tdb.getU2()));
            assertEquals(new ArrayList<AuthToken>(), tokenDAO.read(tdb.getU2()));

            // just to make sure it doesn't crash when you try to clear an empty database
            assertEquals("Successfully cleared", service.run().getMessage());

            db.commit(false);
            db = null;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (db != null) {
                try {
                    db.commit(false);
                    db = null;
                }
                catch (Exception de) {
                    e.printStackTrace();
                }
            }
        }
    }
}