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
    private String personID;
    private String descendant;
    private String firstName;
    private String lastName;
    private String gender;
    private String father;
    private String mother;
    private String spouse;

    public Person() {
        this.personID = "";
        this.descendant = "";
        this.firstName = "";
        this.lastName = "";
        this.gender = "x";
        this.father = null;
        this.mother = null;
        this.spouse = null;
    }

    public Person(String descendant, String fName, String lName, String gender)
            throws InvalidInputException {
        this.personID = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.firstName = fName;
        this.lastName = lName;
        if (!gender.equals("f") && !gender.equals("m")) {
            throw new InvalidInputException();
        }
        this.gender = gender;
        this.father = null;
        this.mother = null;
        this.spouse = null;
    }

    public String getID() {
        return personID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return father;
    }

    public String getMotherID() {
        return mother;
    }

    public String getSpouseID() {
        return spouse;
    }

    public void setID(String ID) {
        this.personID = ID;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setFirstName(String fName) {
        this.firstName = fName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFatherID(String fatherID) {
        this.father = fatherID;
    }

    public void setMotherID(String motherID) {
        this.mother = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouse = spouseID;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        Person rhs = (Person) o;
        if ((father == null) != (rhs.father == null) ||
                (mother == null) != (rhs.mother == null) ||
                (spouse == null) != (rhs.spouse == null)) {
            return false;
        }
        if (father != null && !father.equals(rhs.father)) {
            return false;
        }
        if (mother != null && !mother.equals(rhs.mother)) {
            return false;
        }
        if (spouse != null && !spouse.equals(rhs.spouse)) {
            return false;
        }
        return (personID.equals(rhs.personID) && descendant.equals(rhs.descendant) &&
                firstName.equals(rhs.firstName) && lastName.equals(rhs.lastName) &&
                gender.equals(rhs.gender));
    }
}
