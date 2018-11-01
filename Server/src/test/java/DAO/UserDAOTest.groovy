package DAO

import Model.User

import org.junit.*

import static org.junit.Assert.*

class UserDAOTest {

    private Database db

    @Before
    void setUp() {
        try {
            db = new Database()
            db.openConnection()
        }
        catch (DatabaseException e) {
            e.printStackTrace()
        }
    }

    @After
    void tearDown() {
        try {
            db.closeConnection(false)
            db = null
        }
        catch (DatabaseException e) {
            e.printStackTrace()
        }
    }

    @Test
    void testCreation() {
        UserDAO userDAO = db.getUserDAO()
        User testUser = new User("Name", "password", "email", "Name", "McName", "m")
        userDAO.create(testUser)
        User responseUser = userDAO.read("Name")
        assertEquals(testUser, responseUser)
    }
}
