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
    private UUID ID;
    private User descendant;
    private String fName;
    private String lName;
    private char gender;
    private UUID fatherID;
    private UUID motherID;
    private UUID spouseID;

    public Person(User descendant, String fName, String lName, char gender)
            throws InvalidInputException {
        this.ID = UUID.randomUUID();
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

    public UUID getID() {
        return ID;
    }

    public User getDescendant() {
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

    public UUID getFatherID() {
        return fatherID;
    }

    public UUID getMotherID() {
        return motherID;
    }

    public UUID getSpouseID() {
        return spouseID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public void setDescendant(User descendant) {
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

    public void setFatherID(UUID fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(UUID motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(UUID spouseID) {
        this.spouseID = spouseID;
    }
}
