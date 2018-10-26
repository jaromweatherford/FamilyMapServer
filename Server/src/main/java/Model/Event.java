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

    private UUID ID;
    private User descendant;
    private Person person;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private EventType type;
    private int year;


    public Event(User descendant, Person person, double latitude, double longitude, String country,
                 String city, EventType type, int year) {
        this.ID = UUID.randomUUID();
        this.descendant = descendant;
        this.person = person;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.type = type;
        this.year = year;
    }

    public UUID getID() {
        return ID;
    }

    public User getDescendant() {
        return descendant;
    }

    public Person getPerson() {
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



    public void setID(UUID ID) {
        this.ID = ID;
    }

    public void setDescendant(User descendant) {
        this.descendant = descendant;
    }

    public void setPerson(Person person) {
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
