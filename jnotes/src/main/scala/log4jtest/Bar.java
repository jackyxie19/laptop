package log4jtest;

import org.apache.log4j.Logger;

/**
 * Created by jacky on 2017/7/18.
 */
public class Bar {
    static Logger logger = Logger.getLogger(Bar.class);

    public void doIt() {
        logger.debug("Did it again!");
    }
}
