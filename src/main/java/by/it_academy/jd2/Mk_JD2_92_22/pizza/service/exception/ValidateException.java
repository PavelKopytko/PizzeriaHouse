package by.it_academy.jd2.Mk_JD2_92_22.pizza.service.exception;

public class ValidateException extends Exception {

    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(Exception e) {
        super(e);
    }

    public ValidateException(String message, Exception e) {
        super(message, e);
    }


}
