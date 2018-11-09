package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
import RequestObjects.FillRequest;
import ResponseObjects.MessageResponse;

import static org.junit.Assert.*;

public class FillServiceTest {
    private TestDB tdb;
    private FillService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();
        service = new FillService();
    }

    @After
    public void tearDown() throws Exception {
        new ClearService().run();
    }

    @Test
    public void run() {
        Database db = null;
        try {
            db = Database.instance();
            PersonDAO personDAO = db.getPersonDAO();
            EventDAO eventDAO = db.getEventDAO();

            FillRequest request = new FillRequest(tdb.getU1().getUserName(), 4);
            MessageResponse response = service.run(request);
            assertEquals("Successfully added 31 people and 123 events to the database",
                    response.getMessage());
            assertTrue(personDAO.read(tdb.getU1()).size() == 31);
            assertTrue(eventDAO.read(tdb.getU1()).size() == 123);

            request = new FillRequest(tdb.getU2().getUserName(), 2);
            response = service.run(request);
            assertEquals("Successfully added 7 people and 27 events to the database",
                    response.getMessage());
            assertTrue(personDAO.read(tdb.getU2()).size() == 7);
            assertTrue(eventDAO.read(tdb.getU2()).size() == 27);

            response = null;
            request = new FillRequest("UName3", 3);
            try {
                response = service.run(request);
            }
            catch (Exception e) {

            }
            assertNull(response);

            db.commit(false);
            db = null;
            personDAO = null;
            eventDAO = null;
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