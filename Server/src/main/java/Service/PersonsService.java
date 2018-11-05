package Service;

import java.util.ArrayList;

import DAO.Database;
import DAO.DatabaseException;
import DAO.PersonDAO;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.AuthToken;
import Model.Person;
import Model.User;
import RequestObjects.PersonsRequest;
import ResponseObjects.PersonsResponse;

/** PersonsService handles the model manipulation and database calls for requesting multiple persons
 *
 * @author jarom
 * @version 0.0
 */
public class PersonsService {
    /** run takes a user and returns the people that relate to that user, throws an error if
     * there is no such user
     *
     * @param personsRequest    The information related to the request
     * @return                  A list of the people related to the user
     * @throws Service.UserNotFoundException
     */
    public PersonsResponse run(PersonsRequest personsRequest) throws UserNotFoundException,
                                                                    InternalServerErrorException {
        Database db = null;
        try {
            db = new Database();
            db.openConnection();

            TokenDAO tokenDAO = db.getTokenDAO();
            AuthToken token = tokenDAO.read(personsRequest.getTokenCode());
            UserDAO userDAO = db.getUserDAO();
            User user = userDAO.read(token.getUserName());
            if (user == null) {
                throw new UserNotFoundException();
            }
            PersonDAO personDAO = db.getPersonDAO();
            PersonsResponse personsResponse = new PersonsResponse(personDAO.read(user));

            db.closeConnection(true);
            db = null;
            return personsResponse;
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            try {
                if (db != null) {
                    db.closeConnection(false);
                    db = null;
                }
            }
            catch (DatabaseException de) {
                de.printStackTrace();
                throw new InternalServerErrorException("FAILED TO CLOSE CONNECTION!");
            }
            throw new InternalServerErrorException("Database failed");
        }
    }
}
