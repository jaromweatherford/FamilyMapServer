package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.PersonDAO;
import Model.Person;
import RequestObjects.PersonRequest;
import ResponseObjects.PersonResponse;

/** PersonService handles the model manipulation and database calls for requesting a person
 *
 * @author jarom
 * @version 0.0
 */
public class PersonService {
    /** run takes a UUID and returns the person that has that ID, throws an error if there is
     * no such person
     *
     * @param personRequest     The request information for the desired person
     * @return                  The person with the passed ID
     * @throws Service.PersonNotFoundException
     */
    public PersonResponse run(PersonRequest personRequest) throws PersonNotFoundException,
                                                                    InternalServerErrorException {
        Database db = null;
        try {
            db = new Database();
            db.openConnection();
            PersonDAO personDAO = db.getPersonDAO();
            Person person = personDAO.read(personRequest.getPersonID());
            if (person == null) {
                throw new PersonNotFoundException();
            }
            PersonResponse personResponse = new PersonResponse(person);
            db.closeConnection(true);
            db = null;
            return personResponse;
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
