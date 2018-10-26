package Service;

import RequestObjects.LoadRequest;
import ResponseObjects.MessageResponse;

/** LoadService handles the model manipulation and database calls for loading operations
 *
 * @author jarom
 * @version 0.0
 */
public class LoadService {
    /** run takes a loadRequest, clears the database, fills it with the data in the loadRequest,
     * and returns a success message.
     *
     * @param loadRequest               The data to be loaded into the database
     * @return                          A message detailing the work done
     * @throws InvalidInputException
     */
    public MessageResponse run(LoadRequest loadRequest) throws InvalidInputException {
        throw new InvalidInputException();
    }
}
