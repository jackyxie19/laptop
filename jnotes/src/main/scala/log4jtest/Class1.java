package log4jtest;

import org.apache.log4j.Logger;

import static java.lang.Thread.*;

/**
 * Created by jacky on 2017/7/18.
 */
public class Class1 {
    private static Logger logger = Logger.getLogger(Class1.class);

    public static void main(String[] args) {
//        if (logger.isDebugEnabled())
        try{
        while (true) {
            logger.debug("debug info");
            logger.info("test info");
            java.lang.Thread.sleep(5000l);
        }

        }catch(Exception e){

        }

    }
}
