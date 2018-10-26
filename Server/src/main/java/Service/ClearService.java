package Service;

import ResponseObjects.MessageResponse;

/** ClearService handles the model manipulation and database calls for clearing operations
 *
 * @author jarom
 * @version 0.0
 */
public class ClearService {

    public ClearService() {
    }

    /**
     * run removes everything from the database. No questions asked, none answered.
     * @throws InternalServerErrorException
     */
    public MessageResponse run()  throws InternalServerErrorException {
        throw new InternalServerErrorException("I have not been written yet :(");
    }
}
