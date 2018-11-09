package DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.AuthToken;
import Model.User;
import Service.InvalidInputException;

import static org.junit.Assert.*;

public class TokenDAOTest {
    private User user;
    private Database db;
    private TokenDAO tokenDAO;

    @Before
    public void setUp() throws Exception {
        try {
            db = Database.instance();
            tokenDAO = db.getTokenDAO();
            user = new User("UName", "pword", "fname", "lname",
                    "mail", 'm', "personID");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            db.commit(false);
            db = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createRead() {
        AuthToken authToken = null;
        authToken = new AuthToken(user.getUserName());
        authToken.setCode("CODE");
        assertNotEquals(null, authToken);
        tokenDAO.create(authToken);
        AuthToken authToken2 = tokenDAO.read("CODE");
        assertEquals(authToken, authToken2);
    }

    @Test
    public void read1() {
        ArrayList<AuthToken> authTokens = fill();
        assertTrue(authTokens.size() == 50);

        ArrayList<AuthToken> authTokens2 = tokenDAO.read(user);

        assertEquals(authTokens, authTokens2);
    }

    @Test
    public void destroy() {
        fill();
        empty();
        ArrayList<AuthToken> authTokens = new ArrayList<>();
        ArrayList<AuthToken> authTokens2 = tokenDAO.read(user);
        assertEquals(authTokens, authTokens2);
    }

    @Test
    public void odd() {
        AuthToken authToken = new AuthToken(user.getUserName());
        tokenDAO.destroy(authToken);  // shouldn't crash...
        AuthToken authToken2 = tokenDAO.read(authToken.getCode());
        assertNull(authToken2);
    }

    public ArrayList<AuthToken> fill() {
        AuthToken authToken = null;
        ArrayList<AuthToken> authTokens = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            authToken = new AuthToken(user.getUserName());
            authToken.setCode(Integer.toString(i));
            authTokens.add(authToken);
            tokenDAO.create(authToken);
        }
        return authTokens;
    }

    public void empty() {
        ArrayList<AuthToken> authTokens = tokenDAO.read(user);
        for (AuthToken authToken: authTokens) {
            tokenDAO.destroy(authToken);
        }
    }
}