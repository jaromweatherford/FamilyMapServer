package DAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

import Model.AuthToken;
import Model.User;

/** TokenDAO allows and handles database access for AuthToken objects
 *
 * @author jarom
 * @version 0.0
 */
public class TokenDAO {
    Connection connection;

    public TokenDAO(Connection connection) {
        this.connection = connection;
    }

    /** create adds the passed token to the database, also adds its relations to the database
     *
     * @param token             The token to be added to the database
     * @return                  false if the update failed, true if it succeeded
     */
    public boolean create(AuthToken token) {
        return false;
    }

    /** read takes a UUID and returns a token with that UUID as its ID, or NULL if it doesn't exist
     *
     * @param ID                The ID of the token to be returned
     * @return                  The token with that ID, or NULL if no token has that ID
     */
    public AuthToken read(UUID ID) {
        return null;
    }

    /** read takes a user and returns all tokens related to that user, the list will be empty
     * if the user is not found or if no tokens are found for them
     *
     * @param user              The user who's tokens are desired
     * @return                  A list of all tokens found to relate to that user
     */
    public ArrayList<AuthToken> read(User user) {
        return new ArrayList<>();
    }

    /** destroy takes a token and removes it from the database
     *
     * @param token             The user to be removed
     * @return                  false if the deletion failed, true if it succeeded
     */
    public boolean destroy(AuthToken token) {
        return false;
    }
}
