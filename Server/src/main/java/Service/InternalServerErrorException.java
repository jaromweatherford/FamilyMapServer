package Service;

/** InternalServerErrorException for throwing when the server just isn't working */
public class InternalServerErrorException extends Exception {
    private String message;

    public InternalServerErrorException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
