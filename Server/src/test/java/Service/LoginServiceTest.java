package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAO.Database;
import RequestObjects.LoginRequest;
import ResponseObjects.LoginResponse;

import static org.junit.Assert.*;

public class LoginServiceTest {
    private TestDB tdb;
    private LoginService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();
        service = new LoginService();
    }

    @After
    public void tearDown() throws Exception {
        new ClearService().run();
    }

    @Test
    public void run() {
        AuthorizationService authserv = new AuthorizationService();
        try {
            LoginRequest request = new LoginRequest(tdb.getU1().getUserName(),
                    tdb.getU1().getPassword());
            LoginResponse response = service.run(request);

            assertEquals(tdb.getU1().getUserName(), response.getUserName());
            assertEquals(tdb.getP1().getID(), response.getPersonID());
            assertTrue(authserv.verifyAuthorization(response.getAuthToken()));
            assertTrue(authserv.verifyAuthorization(tdb.getU1().getUserName(),
                    response.getAuthToken()));

            request = new LoginRequest(tdb.getU2().getUserName(),
                    tdb.getU2().getPassword());
            response = service.run(request);

            assertEquals(tdb.getU2().getUserName(), response.getUserName());
            assertEquals(tdb.getP2().getID(), response.getPersonID());
            assertTrue(authserv.verifyAuthorization(response.getAuthToken()));
            assertTrue(authserv.verifyAuthorization(tdb.getU2().getUserName(),
                    response.getAuthToken()));

            request = new LoginRequest("BAD USERNAME", tdb.getU1().getPassword());
            response = null;
            try {
                response = service.run(request);
            }
            catch (Exception e) {
                assertEquals(UserNotFoundException.class, e.getClass());
            }
            assertNull(response);

            request = new LoginRequest(tdb.getU1().getUserName(), "BAD PASSWORD");
            response = null;
            try {
                response = service.run(request);
            }
            catch (Exception e) {
                assertEquals(InvalidInputException.class, e.getClass());
            }
            assertNull(response);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}