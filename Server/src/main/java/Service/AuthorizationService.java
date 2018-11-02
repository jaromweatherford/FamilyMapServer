package Service;

import DAO.Database;
import DAO.DatabaseException;
import DAO.TokenDAO;
import Model.AuthToken;

/**
 * Created by jaromwea on 10/26/18.
 */

public class AuthorizationService {
    public boolean verifyAuthorization(String authToken) throws DatabaseException {
        Database db = null;
        AuthToken token = null;
        try {
            db = new Database();
            db.openConnection();
            TokenDAO tokenDAO = db.getTokenDAO();
            token = tokenDAO.read(authToken);
            db.closeConnection(true);
            db = null;
        }
        finally {
            if (db != null) {
                db.closeConnection(false);
                db = null;
            }
        }
        if (token == null) {
            return false;
        }
        return true;
    }

    public boolean verifyAuthorization(String userName, String authToken) throws DatabaseException {
        Database db = null;
        AuthToken token = null;
        try {
            db = new Database();
            db.openConnection();
            TokenDAO tokenDAO = db.getTokenDAO();
            token = tokenDAO.read(authToken);
            db.closeConnection(true);
            db = null;
        }
        finally {
            if (db != null) {
                db.closeConnection(false);
                db = null;
            }
        }
        if (token == null || !token.getUserName().equals(userName)) {
            return false;
        }
        return true;
    }
}
