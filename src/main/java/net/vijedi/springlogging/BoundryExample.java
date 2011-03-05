package net.vijedi.springlogging;

import net.vijedi.springlogging.exceptions.DomainException;
import net.vijedi.springlogging.exceptions.SystemException;
import net.vijedi.springlogging.interceptor.BoundryLogger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 11:22 AM
 */
@Component
public class BoundryExample {

    private static final Log log = LogFactory.getLog(BoundryExample.class);
    @Autowired
    private Service service;

    public void go() {
        showOldWay();
        showWithAop();
    }

    private void oldServiceException() throws SystemException, DomainException {
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

    private void oldDomainException() throws SystemException, DomainException {
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


    @BoundryLogger
    private void aopServiceException() throws SystemException, DomainException {
       service.getKey();
    }

    @BoundryLogger
    private void aopDomainException() throws SystemException, DomainException {
        service.calculate();
    }

    private void showOldWay() {
        try {
            oldServiceException();
        } catch(Throwable t) {}

        try {
            oldDomainException();
        } catch(Throwable t) {}
    }

    private void showWithAop() {
        try {
            aopServiceException();
        } catch(Throwable t) {}

        try {
            aopDomainException();
        } catch(Throwable t) {}
    }
}
