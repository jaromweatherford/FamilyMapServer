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
import RequestObjects.EventRequest;
import ResponseObjects.EventResponse;

import static org.junit.Assert.*;

public class EventServiceTest {
    private TestDB tdb;
    private EventService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();
        service = new EventService();
    }

    @After
    public void tearDown() throws Exception {
        tdb.dePopulate();
    }

    @Test
    public void run() {
        try {
            EventRequest request = new EventRequest(tdb.getE1().getID(), tdb.getT1().getCode());
            EventResponse response = service.run(request);

            assertEquals(response, new EventResponse(tdb.getE1()));

            request = new EventRequest(tdb.getE2().getID(), tdb.getT2().getCode());
            response = service.run(request);
            assertEquals(response, new EventResponse(tdb.getE2()));

            response = null;
            try {
                request = new EventRequest("UName3", tdb.getT1().getCode());
                response = service.run(request);
            }
            catch (Exception e) {

            }
            assertNull(response);

            response = null;
            try {
                request = new EventRequest(tdb.getE2().getID(), tdb.getT1().getCode());
                response = service.run(request);
            }
            catch (Exception e) {

            }
            assertNull(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}