package rs.ac.uns.quiz.exception;

    public class NotFoundException extends RuntimeException {
        public NotFoundException(String errorMessage) {

            super(errorMessage);
        }
    }

