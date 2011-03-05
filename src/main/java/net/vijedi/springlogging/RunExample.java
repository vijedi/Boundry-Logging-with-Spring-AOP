package net.vijedi.springlogging;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: Tejus Parikh
 * Date: 3/5/11 12:41 PM
 */
public class RunExample {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        BoundryExample example = context.getBean(BoundryExample.class);
        example.go();
    }
}
