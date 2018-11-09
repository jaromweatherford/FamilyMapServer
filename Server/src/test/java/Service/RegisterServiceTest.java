package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.Database;
import DAO.EventDAO;
import DAO.PersonDAO;
import RequestObjects.RegisterRequest;
import ResponseObjects.RegisterResponse;

import static org.junit.Assert.*;

public class RegisterServiceTest {
    private TestDB tdb;
    private RegisterService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        service = new RegisterService();
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

            RegisterRequest request = new RegisterRequest(tdb.getU1().getUserName(),
                    tdb.getU1().getPassword(), tdb.getU1().getEmail(), tdb.getU1().getFirstName(),
                    tdb.getU1().getLastName(), tdb.getU1().getGender());
            RegisterResponse response = service.run(request);
            assertEquals(tdb.getU1().getUserName(), response.getUserName());
            assertTrue(31 == personDAO.read(tdb.getU1()).size());
            assertTrue(123 == eventDAO.read(tdb.getU1()).size());

            response = null;
            try {
                response = service.run(request);
            }
            catch (Exception e) {
                assertEquals(UserNameUnavailableException.class, e.getClass());
            }
            assertNull(response);

            request = new RegisterRequest(tdb.getU2().getUserName(), tdb.getU2().getPassword(),
                    tdb.getU2().getEmail(), tdb.getU2().getFirstName(), tdb.getU2().getLastName(),
                    tdb.getU2().getGender());
            response = service.run(request);
            assertEquals(tdb.getU2().getUserName(), response.getUserName());
            assertTrue(31 == personDAO.read(tdb.getU2()).size());
            assertTrue(123 == eventDAO.read(tdb.getU2()).size());

            response = null;
            try {
                response = service.run(request);
            }
            catch (Exception e) {
                assertEquals(UserNameUnavailableException.class, e.getClass());
            }
            assertNull(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}