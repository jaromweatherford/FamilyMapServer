package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.PersonDAO;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.AuthToken;
import Model.Person;
import Model.User;
import RequestObjects.RegisterRequest;
import ResponseObjects.LoginResponse;
import ResponseObjects.RegisterResponse;

/** RegisterService handles the model manipulation and database calls for registering
 *
 * @author jarom
 * @version 0.0
 */
public class RegisterService {
    /** run takes a user and adds it to the system
     *
     * @param registerRequest       The user to be added
     * @return                      A response with a token, username, and personID
     * @throws UserNameUnavailableException
     */
    public RegisterResponse run(RegisterRequest registerRequest) throws UserNameUnavailableException,
                                                                InternalServerErrorException {
        Database db = null;
        try {
            db = new Database();
            db.openConnection();
            UserDAO userDAO = db.getUserDAO();
            User user = userDAO.read(registerRequest.getUserName());
            if (user != null) {
                db.closeConnection(false);
                throw new UserNameUnavailableException();
            }
            user = generateUser(registerRequest);
            userDAO.create(user);
            AuthToken token = new AuthToken(user.getUsername());
            TokenDAO tokenDAO = db.getTokenDAO();
            tokenDAO.create(token);
            Person person = new Person(user.getUsername(), user.getFirstName(), user.getLastName(),
                                        user.getGender());
            person.setID(user.getUsername());
            PersonDAO personDAO = db.getPersonDAO();
            personDAO.create(person);
            db.closeConnection(true);
            db = null;
            return new RegisterResponse(token.getCode(), user.getUsername(), person.getID());
        }
        catch (DatabaseException e) {
            throw new InternalServerErrorException("Database failure");
        }
        catch (InvalidInputException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Invalid input for person.gender");
        }
    }

    private User generateUser(RegisterRequest rr) {
        User user = new User();
        user.setUsername(rr.getUserName());
        user.setPassword(rr.getPassword());
        user.setEmail(rr.getEmail());
        user.setFirstName(rr.getFirstName());
        user.setLastName(rr.getLastName());
        user.setGender(rr.getGender());
        return user;
    }
}
