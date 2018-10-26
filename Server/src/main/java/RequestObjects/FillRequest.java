package RequestObjects;

import Model.User;

/** FillRequest holds information regarding a client's fill request
 *
 * @author jarom
 * @version 0.0
 */
public class FillRequest {
    private String userName;
    private int generations;

    public FillRequest(String userName, int generations) {
        this.userName = userName;
        this.generations = generations;
    }

    public String getUser() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
