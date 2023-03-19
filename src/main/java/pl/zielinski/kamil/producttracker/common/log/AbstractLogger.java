package pl.zielinski.kamil.producttracker.common.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLogger {
    Logger logger = LoggerFactory.getLogger(getClass());

    public void logInfo(String format, Object... args){
        logger.info(String.format(format, args));
    }

    public void logError(String format, Object... args){
        logger.error(String.format(format, args));
    }
}
