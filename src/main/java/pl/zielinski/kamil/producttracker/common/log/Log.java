package pl.zielinski.kamil.producttracker.common.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Log {
    Logger log = LoggerFactory.getLogger(getClass());

    public void info(String format, Object... args) {
        log.info(String.format(format, args));
    }

    public void error(String format, Object... args) {
        log.error(String.format(format, args));
    }
}
