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
            db = Database.instance();

            UserDAO userDAO = db.getUserDAO();
            User user = userDAO.read(loginRequest.getUserName());
            if (user == null) {
                db.commit(false);
                throw new UserNotFoundException();
            }
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new InvalidInputException();
            }
            AuthToken token = new AuthToken(loginRequest.getUserName());
            TokenDAO tokenDAO = db.getTokenDAO();
            tokenDAO.create(token);
            PersonDAO personDAO = db.getPersonDAO();
            Person person = personDAO.read(user.getPersonID());

            db.commit(true);
            db = null;
            return new LoginResponse(token.getCode(), loginRequest.getUserName(), person.getID());
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Database failed");
        }
        finally {
            if (db != null) {
                try {
                    db.commit(false);
                    db = null;
                }
                catch (DatabaseException e) {
                    System.out.println("FAILED TO CLOSE CONNECTION");
                    throw new InternalServerErrorException("FAILED TO CLOSE CONNECTION!");
                }
            }
        }
    }
}
