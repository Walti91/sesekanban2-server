package sese.exceptions;

public class SeseException extends RuntimeException {

    private SeseError seseError;

    public SeseException(SeseError seseError) {
        super(seseError.getMessage());
        this.seseError = seseError;
    }

    public SeseError getSeseError() {
        return seseError;
    }

    public void setSeseError(SeseError seseError) {
        this.seseError = seseError;
    }
}
