package Service;

import DAO.Database;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

public class TestDB {
    private Database db;
    private User u1;
    private User u2;
    private Person p1;
    private Person p2;
    private Event e1;
    private Event e2;
    private AuthToken t1;
    private AuthToken t2;

    public TestDB() {
        try {
            u1 = new User("uname1", "p1", "m1", "f1", "l1",
                    'm', "pid1");
            u2 = new User("uname2", "p2", "m2", "f2", "l2",
                    'f', "pid2");
            p1 = new Person("uname1", "f1", "l1", "m");
            p1.setID("pid1");
            p2 = new Person("uname2", "f2", "l2", "f");
            p2.setID("pid2");
            e1 = new Event("uname1", "pid1", 1, 1,
                    "USA", "NYC", "t1", 2001);
            e1.setID("eid1");
            e2 = new Event("uname2", "pid2", 2, 2,
                    "USA", "DFW", "t2", 2002);
            e2.setID("eid2");
            t1 = new AuthToken("uname1");
            t1.setCode("c1");
            t2 = new AuthToken("uname2");
            t2.setCode("c2");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populate() {
        try {
            db = Database.instance();

            db.getUserDAO().create(u1);
            db.getUserDAO().create(u2);
            db.getPersonDAO().create(p1);
            db.getPersonDAO().create(p2);
            db.getEventDAO().create(e1);
            db.getEventDAO().create(e2);
            db.getTokenDAO().create(t1);
            db.getTokenDAO().create(t2);

            db.commit(true);
            db = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null) {
                try {
                    db.commit(false);
                    db = null;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void dePopulate() {
        try {
            db = Database.instance();

            db.getUserDAO().destroy(u1);
            db.getUserDAO().destroy(u2);
            db.getPersonDAO().destroy(p1);
            db.getPersonDAO().destroy(p2);
            db.getEventDAO().destroy(e1);
            db.getEventDAO().destroy(e2);
            db.getTokenDAO().destroy(t1);
            db.getTokenDAO().destroy(t2);

            db.commit(true);
            db = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (db != null) {
                try {
                    db.commit(false);
                    db = null;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public User getU1() {
        return u1;
    }

    public User getU2() {
        return u2;
    }

    public Person getP1() {
        return p1;
    }

    public Person getP2() {
        return p2;
    }

    public Event getE1() {
        return e1;
    }

    public Event getE2() {
        return e2;
    }

    public AuthToken getT1() {
        return t1;
    }

    public AuthToken getT2() {
        return t2;
    }
}
