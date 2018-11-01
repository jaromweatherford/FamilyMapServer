package Model;

import java.util.ArrayList;
import java.util.UUID;

import Service.InvalidInputException;

/** Person holds all the information directly relating to an individual person
 *
 * @author jarom
 * @version 0.0
 */
public class Person {
    private String ID;
    private String descendant;
    private String fName;
    private String lName;
    private char gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    public Person() {
        this.ID = "";
        this.descendant = "";
        this.fName = "";
        this.lName = "";
        this.gender = 'x';
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    public Person(String descendant, String fName, String lName, char gender)
            throws InvalidInputException {
        this.ID = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.fName = fName;
        this.lName = lName;
        if (gender != 'f' && gender != 'm') {
            throw new InvalidInputException();
        }
        this.gender = gender;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    public String getID() {
        return ID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public char getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setFirstName(String fName) {
        this.fName = fName;
    }

    public void setLastName(String lName) {
        this.lName = lName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
