package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.TokenDAO;
import DAO.UserDAO;
import Model.AuthToken;

/**
 * Created by jaromwea on 10/26/18.
 */

public class AuthorizationService {
    public boolean verifyAuthorization(String authToken) throws InternalServerErrorException {
        Database db = null;
        AuthToken token = null;
        try {
            db = Database.instance();

            TokenDAO tokenDAO = db.getTokenDAO();
            token = tokenDAO.read(authToken);
            if (token == null) {
                return false;
            }

            db.commit(true);
            db = null;
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
        return true;
    }

    public boolean verifyAuthorization(String userName, String authToken) throws InternalServerErrorException {
        Database db = null;
        AuthToken token = null;
        try {
            db = Database.instance();

            TokenDAO tokenDAO = db.getTokenDAO();
            token = tokenDAO.read(authToken);
            if (token == null || !token.getUserName().equals(userName)) {
                return false;
            }

            db.commit(true);
            db = null;
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
        return true;
    }
}
