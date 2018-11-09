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
import RequestObjects.PersonRequest;
import ResponseObjects.PersonResponse;

import static org.junit.Assert.*;

public class PersonServiceTest {
    private TestDB tdb;
    private PersonService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();
        service = new PersonService();
    }

    @After
    public void tearDown() throws Exception {
        tdb.dePopulate();
    }

    @Test
    public void run() {
        try {
            PersonRequest request = new PersonRequest(tdb.getP1().getID(), tdb.getT1().getCode());
            PersonResponse response = service.run(request);

            assertEquals(response, new PersonResponse(tdb.getP1()));

            request = new PersonRequest(tdb.getP2().getID(), tdb.getT2().getCode());
            response = service.run(request);
            assertEquals(response, new PersonResponse(tdb.getP2()));
            response = null;
            try {
                request = new PersonRequest("UName3", tdb.getT1().getCode());
                response = service.run(request);
            }
            catch (Exception e) {

            }
            assertNull(response);

            response = null;
            try {
                request = new PersonRequest(tdb.getP1().getID(), tdb.getT2().getCode());
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