package RequestObjects;

public class EventsRequest {
    private String tokenCode;

    public EventsRequest(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }
}
