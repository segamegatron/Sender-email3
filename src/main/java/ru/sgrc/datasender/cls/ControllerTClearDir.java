package ru.sgrc.datasender.cls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sgrc.datasender.Main;

public class ControllerTClearDir {

    private static final Logger logger = LoggerFactory.getLogger(
            ControllerTClearDir.class);
    public ControllerTClearDir(String path, int delay) throws InterruptedException {
        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
        logger.debug("Main.secondsControllerClearDir {}", Main.secondsControllerClearDir);
        TClearDir tClearDir = new TClearDir(path, delay);
        tClearDir.tClearDir();
    }


}
