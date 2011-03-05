package net.vijedi.springlogging;

import net.vijedi.springlogging.exceptions.DomainException;
import net.vijedi.springlogging.exceptions.SystemException;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 1:20 PM
 */
public interface BoundryInterface {

    void callGetKey() throws SystemException, DomainException;
    void callCalcuate() throws SystemException, DomainException;

}
