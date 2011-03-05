package net.vijedi.springlogging.exceptions;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 11:26 AM
 */
public class DomainException extends Exception {
    public DomainException() {
    }

    public DomainException(String s) {
        super(s);
    }

    public DomainException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DomainException(Throwable throwable) {
        super(throwable);
    }
}
