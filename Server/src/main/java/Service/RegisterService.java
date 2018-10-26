package Service;

import RequestObjects.RegisterRequest;
import ResponseObjects.LoginResponse;

/** RegisterService handles the model manipulation and database calls for registering
 *
 * @author jarom
 * @version 0.0
 */
public class RegisterService {
    /** run takes a user and adds it to the system
     *
     * @param user                  The user to be added
     * @return                      A response with a token, username, and personID
     * @throws UserNameUnavailableException
     */
    public LoginResponse run(RegisterRequest registerRequest) throws UserNameUnavailableException {
        throw new UserNameUnavailableException();
    }
}
