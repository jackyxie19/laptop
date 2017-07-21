package log4jtest;

import org.apache.log4j.Logger;

/**
 * Created by jacky on 2017/7/18.
 */
public class Class2 {
//    private static Logger logger = Logger.getLogger("class2");
    private static Logger logger = Logger.getLogger(Class2.class);

    public static void main(String[] args) {
//        if (logger.isDebugEnabled())
            logger.debug("debug info");
        logger.info("test info");
    }
}
