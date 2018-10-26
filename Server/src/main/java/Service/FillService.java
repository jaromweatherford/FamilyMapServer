package Service;

import RequestObjects.FillRequest;
import ResponseObjects.MessageResponse;

/** FillService handles the model manipulation and database calls for fill operations
 *
 * @author jarom
 * @version 0.0
 */
public class FillService {
    /** run takes a user and a number of generations to generate, clears any previous data
     * for that user, and generates randomized ancestor data to the given number of generations
     *
     * @param user                      The user who's getting a new family
     * @param generations               The number of generations of ancestors to make
     * @return                          A message describing the data generated
     * @throws UserNotFoundException
     */
    public MessageResponse run(FillRequest fillRequest) throws UserNotFoundException,
            InvalidInputException {
        if (fillRequest.getGenerations() < 0) {
            throw new InvalidInputException();
        }
        throw new UserNotFoundException();
    }
}
