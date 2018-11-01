package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.PersonDAO;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.AuthToken;
import Model.Person;
import Model.User;
import RequestObjects.LoginRequest;
import ResponseObjects.LoginResponse;

/** LoginService handles the model manipulation and database calls for logging in
 *
 * @author jarom
 * @version 0.0
 */
public class LoginService {
    /** run generates an AuthToken and returns it with the user's username and personID
     *
     * @param loginRequest          The user to be logged in
     * @return                      A response with a token, username, and personID
     * @throws UserNotFoundException
     */
    public LoginResponse run(LoginRequest loginRequest) throws UserNotFoundException,
                                                        InternalServerErrorException,
                                                        InvalidInputException {
        Database db = null;
        try {
            db = new Database();
            db.openConnection();
            UserDAO userDAO = db.getUserDAO();
            User user = userDAO.read(loginRequest.getUserName());
            if (user == null) {
                db.closeConnection(false);
                throw new UserNotFoundException();
            }
            AuthToken token = new AuthToken(loginRequest.getUserName());
            TokenDAO tokenDAO = db.getTokenDAO();
            tokenDAO.create(token);
            db.closeConnection(true);
            db = null;
            return new LoginResponse(token.getCode(), loginRequest.getUserName(), loginRequest.getPassword());
        }
        catch (DatabaseException e) {
            throw new InternalServerErrorException("Database failure");
        }
        finally {
            if (db != null) {
                try {
                    db.closeConnection(false);
                    db = null;
                }
                catch (DatabaseException e) {
                    throw new InternalServerErrorException("FAILED TO CLOSE CONNECTION!");
                }
            }
        }
    }
}
