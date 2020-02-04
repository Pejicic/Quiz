package rs.ac.uns.quiz.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}