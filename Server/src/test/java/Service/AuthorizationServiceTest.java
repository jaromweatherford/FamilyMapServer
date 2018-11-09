package Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import DAO.Database;
import DAO.DatabaseException;
import DAO.TokenDAO;
import Model.AuthToken;

import static org.junit.Assert.*;

public class AuthorizationServiceTest {
    private TestDB tdb;
    private AuthorizationService service;

    @Before
    public void setUp() throws Exception {
        tdb = new TestDB();
        tdb.populate();;
        service = new AuthorizationService();
    }

    @After
    public void tearDown() throws Exception {
        tdb.dePopulate();
    }

    @Test
    public void verifyAuthorization() {
        try {
            assertTrue(service.verifyAuthorization("code1"));
            assertTrue(service.verifyAuthorization( "UName1","code1"));
            assertTrue(service.verifyAuthorization("code2"));
            assertFalse(service.verifyAuthorization("code"));
            assertFalse(service.verifyAuthorization("code3"));
            assertFalse(service.verifyAuthorization("keopeqg"));
        }
        catch (InternalServerErrorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verifyAuthorization1() {
        try {
            assertTrue(service.verifyAuthorization("UName1", "code1"));
            assertTrue(service.verifyAuthorization("UName2", "code2"));
            assertFalse(service.verifyAuthorization("UName1", "code2"));
            assertFalse(service.verifyAuthorization("UName2", "code1"));
            assertFalse(service.verifyAuthorization("code1", "code1"));
            assertFalse(service.verifyAuthorization("UName2", "UName2"));
            assertFalse(service.verifyAuthorization("UName1", "ocewiv"));
            assertFalse(service.verifyAuthorization("nvwoi", "code1"));
        }
        catch (InternalServerErrorException e) {
            e.printStackTrace();
        }
    }
}