package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception;

public class NotUniqServiceException extends Exception {

    public NotUniqServiceException() {
    }

    public NotUniqServiceException(String message) {
        super(message);
    }

    public NotUniqServiceException(Exception e) {
        super(e);
    }

    public NotUniqServiceException(String message, Exception e) {
        super(message, e);
    }


}
