package DAO;

/**
 * Created by jaromwea on 10/26/18.
 */

public class DatabaseException extends Exception {

    private String message;

    public DatabaseException(String message) {
        this.message = message;
    }

}
