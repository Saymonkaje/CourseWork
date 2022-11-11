package logger;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MyLogger {
    private static final Logger logger = LogManager.getLogger();;


    public static Logger getLogger()
    {
        return logger;
    }


}
