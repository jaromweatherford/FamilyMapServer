package DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Person;
import Model.User;
import Service.InvalidInputException;

import static org.junit.Assert.*;

public class PersonDAOTest {
    private User user;
    private Database db;
    private PersonDAO personDAO;

    @Before
    public void setUp() throws Exception {
        try {
            db = Database.instance();
            personDAO = db.getPersonDAO();
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
        Person person = null;
        try {
            person = new Person(user.getUserName(), "fname", "lname",
                    "m");
            person.setID("ID");
            person.setFatherID("daddy");
            person.setMotherID("mommy");
            person.setSpouseID("wifey");
        }
        catch (InvalidInputException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, person);
        personDAO.create(person);
        Person person2 = personDAO.read("ID");
        assertEquals(person, person2);
    }

    @Test
    public void read1() {
        ArrayList<Person> persons = fill();
        assertTrue(persons.size() == 50);

        ArrayList<Person> persons2 = personDAO.read(user);

        assertEquals(persons, persons2);
    }

    @Test
    public void destroy() {
        fill();
        empty();
        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Person> persons2 = personDAO.read(user);
        assertEquals(persons, persons2);
    }

    @Test
    public void odd() {
        Person person = null;
        try {
            person = new Person(user.getUserName(), "fname", "lname",
                    "m");
        }
        catch (InvalidInputException e){
            e.printStackTrace();
        }
        personDAO.destroy(person);  // shouldn't crash...
        Person person2 = personDAO.read(person.getID());
        assertNull(person2);
    }

    public ArrayList<Person> fill() {
        Person person = null;
        ArrayList<Person> persons = new ArrayList<>();
        try {
            for (int i = 0; i < 50; ++i) {
                person = new Person(user.getUserName(), "fname",
                        "lname", (i % 2 == 0 ? "m" : "f"));
                person.setID("ID" + Integer.toString(i));
                person.setFatherID("daddy" + Integer.toString(i));
                person.setMotherID("mommy" + Integer.toString(i));
                person.setSpouseID("spouse" + Integer.toString(i));
                persons.add(person);
                personDAO.create(person);
            }
        }
        catch (InvalidInputException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public void empty() {
        ArrayList<Person> persons = personDAO.read(user);
        for (Person person: persons) {
            personDAO.destroy(person);
        }
    }
}