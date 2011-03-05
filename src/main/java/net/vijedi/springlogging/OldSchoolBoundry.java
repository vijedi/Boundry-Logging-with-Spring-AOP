package net.vijedi.springlogging;

import net.vijedi.springlogging.exceptions.DomainException;
import net.vijedi.springlogging.exceptions.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 1:51 PM
 */
@Component("oldSchoolBoundry")
public class OldSchoolBoundry implements BoundryInterface {

    private static final Log log = LogFactory.getLog(OldSchoolBoundry.class);

    @Autowired
    private Service service;

    public void callGetKey() throws SystemException, DomainException {
        if(log.isDebugEnabled()) {
            log.debug("entering oldServiceException");
        }

        try {
            service.getKey();
        } catch(DomainException e) {
            // Allow the system exception to bubble but
            // process any domain exception
            log.error("Domain exception occurred", e);
        }

        if(log.isDebugEnabled()) {
            log.debug("exiting oldServiceException");
        }
    }

    public void callCalcuate() throws SystemException, DomainException {
        if(log.isDebugEnabled()) {
            log.debug("entering oldServiceException");
        }

        try {
            service.calculate();
        } catch(DomainException e) {
            // Allow the system exception to bubble but
            // process any domain exception
            log.error("Domain exception occurred", e);
        }

        if(log.isDebugEnabled()) {
            log.debug("exiting oldServiceException");
        }
    }
}
