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
import RequestObjects.EventRequest;
import RequestObjects.EventsRequest;
import ResponseObjects.EventResponse;
import ResponseObjects.EventsResponse;

import static org.junit.Assert.*;

public class EventsServiceTest {
    private TestDB tdb;
    private EventsService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();
        service = new EventsService();
    }

    @After
    public void tearDown() throws Exception {
        tdb.dePopulate();
    }

    @Test
    public void run() {
        try {
            EventsRequest request = new EventsRequest(tdb.getT1().getCode());
            EventsResponse response = service.run(request);
            assertTrue(response.size() == 1);

            request = new EventsRequest(tdb.getT2().getCode());
            response = service.run(request);
            assertTrue(response.size() == 1);

            response = null;
            request = new EventsRequest("code3");
            try {
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