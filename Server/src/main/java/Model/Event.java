package Model;

import java.util.Date;
import java.util.UUID;

/** Event holds all the information directly relating to a particular event or occurrence
 *
 * @author jarom
 * @version 0.0
 */
public class Event {

    private String eventID;
    private String descendant;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;


    public Event() {
        this.eventID = "";
        this.descendant = "";
        this.personID = "";
        this.latitude = 0;
        this.longitude = 0;
        this.country = "";
        this.city = "";
        this.eventType = "";
        this.year = 0;
    }

    public Event(String descendant, String person, double latitude, double longitude, String country,
                 String city, String type, int year) {
        this.eventID = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.personID = person;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = type;
        this.year = year;
    }

    public String getID() {
        return eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPerson() {
        return personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }



    public void setID(String ID) {
        this.eventID = ID;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setPerson(String person) {
        this.personID = person;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setType(String type) {
        this.eventType = type;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
