package rs.ac.uns.quiz.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String errorMessage) {

        super(errorMessage);
    }
}
