package Service;

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
     * @param user                  The user to be logged in
     * @return                      A response with a token, username, and personID
     * @throws UserNotFoundException
     */
    public LoginResponse run(LoginRequest loginRequest) throws UserNotFoundException{
        throw new UserNotFoundException();
    }
}
