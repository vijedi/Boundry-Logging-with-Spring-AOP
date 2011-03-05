package net.vijedi.springlogging.exceptions;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 11:26 AM
 */
public class SystemException extends Exception {
    public SystemException() {
    }

    public SystemException(String s) {
        super(s);
    }

    public SystemException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SystemException(Throwable throwable) {
        super(throwable);
    }
}
