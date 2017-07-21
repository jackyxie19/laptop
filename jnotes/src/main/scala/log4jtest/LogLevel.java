package log4jtest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by jacky on 2017/7/18.
 */
public class LogLevel {
    public static void main(String[] args) {
        // get a logger instance named "com.foo"
        Logger logger = Logger.getLogger("com.foo");

        // Now set its level. Normally you do not need to set the
        // level of a logger programmatically. This is usually done
        // in configuration files.
        logger.setLevel(Level.INFO);

        Logger barlogger = Logger.getLogger("com.foo.Bar");

        // This request is enabled, because WARN >= INFO.
        logger.warn("Low fuel level.");

        // This request is disabled, because DEBUG < INFO.
        logger.debug("Starting search for nearest gas station.");

        // The logger instance barlogger, named "com.foo.Bar",
        // will inherit its level from the logger named
        // "com.foo" Thus, the following request is enabled
        // because INFO >= INFO.
        barlogger.info("Located nearest gas station.");

        // This request is disabled, because DEBUG < INFO.
        barlogger.debug("Exiting gas station search");
    }
}
