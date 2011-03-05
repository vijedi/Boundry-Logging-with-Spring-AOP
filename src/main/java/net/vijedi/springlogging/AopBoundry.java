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
 * Date: 3/5/11 1:22 PM
 */
@Component("aopBoundry")
public class AopBoundry implements BoundryInterface {

    private static final Log log = LogFactory.getLog(AopBoundry.class);
    
    @Autowired
    private Service service;

    @BoundryLogger
    public void callCalcuate() throws SystemException, DomainException {
         service.calculate();
    }

    @BoundryLogger
    public void callGetKey() throws SystemException, DomainException {
         service.getKey();
    }
}
