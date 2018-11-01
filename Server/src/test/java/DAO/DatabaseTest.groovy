package DAO

import org.junit.After
import org.junit.Before
import org.junit.Test

class DatabaseTest extends groovy.util.GroovyTestCase {

    private Database db;

    @Before
    void setUp() {
        db = new Database()
        db.openConnection()
    }

    @After
    void tearDown() {
        db.closeConnection()
        db = null
    }
}
