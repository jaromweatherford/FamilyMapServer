package Model;

import java.util.UUID;

import Service.InvalidInputException;

/** User holds all the information directly relating to a user profile
 *
 * @author jarom
 * @version 0.0
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String fName;
    private String lName;
    private char gender;


    public User(String username, String password, String email, String fName, String lName,
                char gender) throws InvalidInputException {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        if (gender != 'f' && gender != 'm') {
            throw new InvalidInputException();
        }
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
