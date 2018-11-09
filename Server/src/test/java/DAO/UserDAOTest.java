package DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.User;
import Service.InvalidInputException;

import static org.junit.Assert.*;

public class UserDAOTest {
    private Database db;
    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        try {
            db = Database.instance();
            userDAO = db.getUserDAO();
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
        User user = null;
        try {
            user = new User("TESTNAME", "TESTPASSWORD", "mail",
                    "fname", "lname", 'm', "personID");
        }
        catch (InvalidInputException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, user);
        userDAO.create(user);
        User user2 = userDAO.read("TESTNAME");
        assertEquals(user, user2);
    }

    @Test
    public void read1() {
        ArrayList<User> users = fill();
        assertTrue(users.size() == 50);

        ArrayList<User> users2 = userDAO.read();

        assertEquals(users, users2);
    }

    @Test
    public void destroy() {
        fill();
        empty();
        ArrayList<User> users = new ArrayList<>();
        ArrayList<User> users2 = userDAO.read();
        assertEquals(users, users2);
    }

    @Test
    public void odd() {
        User user = null;
        try {
            user = new User("UName", "PWord", "mail", "fname",
                    "lname", 'm', "personID");
        }
        catch (InvalidInputException e){
            e.printStackTrace();
        }
        userDAO.destroy(user);  // shouldn't crash...
        User user2 = userDAO.read(user.getUserName());
        assertNull(user2);
    }

    public ArrayList<User> fill() {
        User user = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            for (int i = 0; i < 50; ++i) {
                user = new User(Integer.toString(i), "TESTPASSWORD", "mail",
                        "fname", "lname", (i % 2 == 0 ? 'm' : 'f'),
                        "personID" + Integer.toString(i));
                users.add(user);
                userDAO.create(user);
            }
        }
        catch (InvalidInputException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void empty() {
        ArrayList<User> users = userDAO.read();
        for (User user: users) {
            userDAO.destroy(user);
        }
    }
}