package net.vijedi.springlogging;

import net.vijedi.springlogging.exceptions.DomainException;
import net.vijedi.springlogging.exceptions.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * This class is an example service that throws
 * exceptions that need to be caught at the boundry
 *
 * The Service encapsulates exceptions into one of two
 * types, DomainException (which signals that something
 * unexpected happened while processing) and SystemException
 * (for when some piece of infrastructure has failed).
 *
 * Author: Tejus Parikh
 * Date: 3/5/11 11:27 AM
 */
@Component
public class Service {

    private static final Log log = LogFactory.getLog(Service.class);

    public double calculate() throws SystemException, DomainException {
        // Pretend that the inputs to this function are not valid
        // we should then return a domain exception to signal that
        // the calculation could not be made
        throw new DomainException("Bad inputs");
    }

    public long getKey() throws SystemException, DomainException {

        try {
            // Assume we couldn't get the key from the datastore
            // that would be a system exception since it's a
            // communication failure with another component
            throw new Exception("Unable to communicate");

        } catch(Exception e) {
            // Log this locally since the administrator of this
            // component needs to know about the failure.
            log.error(e.getMessage(), e);

            // Now bubble a new exception signifying an error
            // has occured
            throw new SystemException("Unable to reach datastore");
        }
    }
}
