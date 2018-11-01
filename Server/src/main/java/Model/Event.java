package Model;

import java.util.Date;
import java.util.UUID;

/** Event holds all the information directly relating to a particular event or occurrence
 *
 * @author jarom
 * @version 0.0
 */
public class Event {

    public enum EventType {
        BIRTH, DEATH, MARRIAGE, BAPTISM, CHRISTENING
    }

    private String ID;
    private String descendant;
    private String person;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private EventType type;
    private int year;


    public Event() {
        this.ID = "";
        this.descendant = "";
        this.person = "";
        this.latitude = 0;
        this.longitude = 0;
        this.country = "";
        this.city = "";
        this.type = EventType.BIRTH;
        this.year = 0;
    }

    public Event(String descendant, String person, double latitude, double longitude, String country,
                 String city, EventType type, int year) {
        this.ID = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.person = person;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.type = type;
        this.year = year;
    }

    public String getID() {
        return ID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPerson() {
        return person;
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

    public EventType getType() {
        return type;
    }

    public int getYear() {
        return year;
    }



    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public void setPerson(String person) {
        this.person = person;
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

    public void setType(EventType type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
