package net.vijedi.springlogging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 12:41 PM
 */
public class RunExample {

    private static final Log log = LogFactory.getLog(RunExample.class);
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        
        BoundryInterface oldSchool = context.getBean("oldSchoolBoundry", BoundryInterface.class);
        try {
            oldSchool.callCalcuate();
        } catch (Throwable t) {
            // Do nothing error should be logged
        }

        try {
            oldSchool.callGetKey();
        } catch (Throwable t) {
            // Do nothing error should be logged
        }

        BoundryInterface aop = context.getBean("aopBoundry", BoundryInterface.class);
        try {
            aop.callCalcuate();
        } catch (Throwable t) {
            // Do nothing error should be logged
        }

        try {
            aop.callGetKey();
        } catch (Throwable t) {
            // Do nothing error should be logged
        }
    }
}
