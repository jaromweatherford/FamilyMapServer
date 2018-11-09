package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.PersonDAO;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.AuthToken;
import Model.Person;
import Model.User;
import RequestObjects.FillRequest;
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
            db = Database.instance();

            UserDAO userDAO = db.getUserDAO();
            User user = userDAO.read(registerRequest.getUserName());
            if (user != null) {
                db.commit(false);
                throw new UserNameUnavailableException();
            }
            user = generateUser(registerRequest);
            userDAO.create(user);
            AuthToken token = new AuthToken(user.getUserName());
            TokenDAO tokenDAO = db.getTokenDAO();
            tokenDAO.create(token);
            FillRequest fillRequest = new FillRequest(registerRequest.getUserName(), 4);
            new FillService().run(fillRequest);
            PersonDAO personDAO = db.getPersonDAO();
            Person person = personDAO.read(user.getUserName());
            db.commit(true);
            db = null;
            return new RegisterResponse(token.getCode(), user.getUserName(), person.getID());
        }
        catch (DatabaseException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Database failed");
        }
        catch (InvalidInputException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Invalid input for person.gender");
        }
        catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Now I know I had that user just a second ago...");
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

    private User generateUser(RegisterRequest rr) {
        User user = new User();
        user.setUserName(rr.getUserName());
        user.setPassword(rr.getPassword());
        user.setEmail(rr.getEmail());
        user.setFirstName(rr.getFirstName());
        user.setLastName(rr.getLastName());
        user.setGender(rr.getGender());
        user.setPersonID(rr.getUserName());
        return user;
    }
}
