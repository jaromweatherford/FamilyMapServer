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
import RequestObjects.PersonsRequest;
import ResponseObjects.PersonsResponse;

import static org.junit.Assert.*;

public class PersonsServiceTest {
    private TestDB tdb;
    private PersonsService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();
        service = new PersonsService();
    }

    @After
    public void tearDown() throws Exception {
        tdb.dePopulate();
    }

    @Test
    public void run() {
        try {
            PersonsRequest request = new PersonsRequest(tdb.getT1().getCode());
            PersonsResponse response = service.run(request);
            assertTrue(response.size() == 1);

            request = new PersonsRequest(tdb.getT2().getCode());
            response = service.run(request);
            assertTrue(response.size() == 1);

            response = null;
            request = new PersonsRequest("code3");
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