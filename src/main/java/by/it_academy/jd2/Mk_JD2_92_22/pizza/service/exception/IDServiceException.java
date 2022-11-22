package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception;

public class IDServiceException extends Exception {

    public IDServiceException() {
    }

    public IDServiceException(String message) {
        super(message);
    }

    public IDServiceException(Exception e) {
        super(e);
    }

    public IDServiceException(String message, Exception e) {
        super(message, e);
    }


}
