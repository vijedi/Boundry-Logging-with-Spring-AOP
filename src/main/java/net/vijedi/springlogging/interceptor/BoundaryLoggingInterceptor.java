package net.vijedi.springlogging.interceptor;

import net.vijedi.springlogging.exceptions.DomainException;
import net.vijedi.springlogging.exceptions.SystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 11:26 AM
 */
@Aspect
@Service
public class BoundaryLoggingInterceptor {

    private static final Log log = LogFactory.getLog(BoundaryLoggingInterceptor.class);

    /**
     * Log entering into a method
     * @param jp
     */
    @Before("@annotation(net.vijedi.springlogging.interceptor.BoundaryLogger)")
    public void logInvocation(JoinPoint jp) {
        Log log = getLog(jp);
        if (log.isDebugEnabled()) {
            Method met = getMethod(jp);

            // Create the log string with the method name
            StringBuilder builder = new StringBuilder("Calling: ");
            builder.append(met.getName()).append("(");
            boolean hasArgs = false;
            for(Object o : jp.getArgs()) {
                if(o != null) {
                    hasArgs = true;
                    builder.append("<").append(o.getClass().getSimpleName()).append(">");
                    builder.append(o.toString());
                    try {
                        builder.append(", ");
                    } catch(NullPointerException e) {
                        builder.append("null, ");
                    }
                }
            }

            if(hasArgs) {
                builder.substring(0, builder.length() - 2);
            }
            builder.append(")");
            log.debug(builder.toString());
        }
    }


    /**
     * Log that we are leaving a method.
     * @param jp
     * @param retVal
     */
    @AfterReturning(
            pointcut = "@annotation(net.vijedi.springlogging.interceptor.BoundaryLogger)",
            returning = "retVal")
    public void logComplete(JoinPoint jp, Object retVal) {
        Log log = getLog(jp);
        logReturn(jp, log);
    }

    /**
     * Log that an exception has occurred and bubble.
     * @param jp
     * @param ex
     * @throws SystemException
     * @throws DomainException
     */
    @AfterThrowing(
            pointcut = "@annotation(net.vijedi.springlogging.interceptor.BoundaryLogger)",
            throwing = "ex"
    )
    public void processException(JoinPoint jp, Throwable ex) throws SystemException, DomainException {
        if(ex instanceof SystemException) {
            // System exceptions were logged at source
            // do not log the exception, just the return
            logReturn(jp, getLog(jp));
            throw (SystemException) ex;
        } else if(ex instanceof DomainException) {
            logException(jp, ex);
            throw (DomainException) ex;
        } else {
            logException(jp, ex);
            throw new DomainException(ex);
        }

    }

    private void logException(JoinPoint jp, Throwable ex) {
        Log log = getLog(jp);
        log.error(ex.getMessage(), ex);
        logReturn(jp, log);
    }

    private void logReturn(JoinPoint jp, Log log) {
        if(log.isDebugEnabled()) {
            Method met = getMethod(jp);
            log.debug("Completed " + met.getName());
        }
    }

    /**
     * Get the method that this join point surrounds.
     * Used for logging the entry into a method.
     *
     * @param jp
     * @return
     */
    protected Method getMethod(JoinPoint jp) {
        Method invoked = null;
        try {
            MethodSignature met = (MethodSignature) jp.getSignature();
            invoked = jp.getSourceLocation().getWithinType().getMethod(
                    met.getMethod().getName(),
                    met.getMethod().getParameterTypes());
        } catch(NoSuchMethodException e) {
            log.error("Unable to get the method for logging");
            // squash it here instead of letting it bubble up.
        }
        return invoked;
    }

    /**
     * Get a logger in the context of the class instead of the
     * context of the interceptor. Otherwise every log message
     * will look like it's coming from here.
     *
     * @param jp
     * @return
     */
    protected Log getLog(JoinPoint jp) {
        return LogFactory.getLog(jp.getTarget().getClass());
    }
}
