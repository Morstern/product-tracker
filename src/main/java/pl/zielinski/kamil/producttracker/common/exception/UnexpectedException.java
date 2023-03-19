package pl.zielinski.kamil.producttracker.common.exception;

public class UnexpectedException extends RuntimeException{
    public UnexpectedException() {
        super();
    }
    public UnexpectedException(String message) {
        super(message);
    }
}
