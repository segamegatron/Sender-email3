package ru.sgrc.datasender.cls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sgrc.datasender.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class TClearDir {
    public String PATH_DIR;
    public final int local = 3;
    public long delay;
    //    File dir = new File(PATH_DIR);
//    final File dir;
    String formatDate = "YYYYMMdd";
    String formateDateFull = "yyyyMMddHHmmss";
    String HMS = "HHmmss";
    Integer currentDate = Integer.parseInt(new SimpleDateFormat(formatDate).format(Calendar.getInstance().getTime()));
    //    Integer currentDate = Integer.parseInt(new SimpleDateFormat(HMS).format(Calendar.getInstance().getTime()));
    Integer H = Integer.parseInt(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()));
    Integer m = Integer.parseInt(new SimpleDateFormat("mm").format(Calendar.getInstance().getTime()));
    Integer s = Integer.parseInt(new SimpleDateFormat("ss").format(Calendar.getInstance().getTime()));

    Path file;
    BasicFileAttributes attr;

    String sDateCreateFile;
    Logger logger = LoggerFactory.getLogger(TClearDir.class);
    private float SS;
    private int MM;
    private int HH;
    private float timeFile;
    private float timeNow;

    public TClearDir(String path, String[] nameFileToDelete) throws IOException {
        this.PATH_DIR = path;
        for (String nameFile : nameFileToDelete) {
            logger.debug(nameFile);
            logger.debug("Подготвка к удалению файла/ов {} ", nameFile);
            clear(path, nameFile);
        }
//        this.bDate = bDate;
//        this.fDate = fDate;

    }


    public TClearDir(String PATH_DIR, int delay) throws InterruptedException {
        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
        logger.debug("Проверка нужно ли к удалять файл/ы");
        this.delay = delay * 1000L;
        this.PATH_DIR = PATH_DIR;

    }

    public void tClearDir() throws InterruptedException {
        File dir = new File(this.PATH_DIR);
        boolean step = false;
        while (true) {
            Main.secondsControllerClearDir = Main.getSecondsTimeNow();
            if (step) {
                Thread.sleep(delay);
            }
//            step = true;
            Main.secondsControllerClearDir = Main.getSecondsTimeNow();
//            logger.debug("secondsControllerClearDir из TClearDir {} ", Main.secondsControllerClearDir);
//            logger.debug("secondsControllerClearDir из main {} ", Main.secondsControllerClearDir);

            if (Objects.requireNonNull(dir.list()) == null) {
                /*todo создать директорию*/
            }
            for (String fileName : Objects.requireNonNull(dir.list())) {

                try {
                    logger.debug(PATH_DIR + fileName);
                    file = Paths.get(PATH_DIR + fileName);
                    attr = Files.readAttributes(file, BasicFileAttributes.class);

                    logger.debug("time create {}  - file - {}", attr.creationTime(), fileName);

                    FileTime i = attr.creationTime();
                    logger.debug("i.toMillis() {}", i.toString());
//                    logger.debug("i.toMillis() {}");
                    logger.debug("i.toMillis() {}", i.toMillis());
                    String sDateCreateFileHMS = String.valueOf(attr.creationTime()).split("T")[1];
                    HH = Integer.parseInt(sDateCreateFileHMS.split(":")[0]) + this.local;
                    MM = Integer.parseInt(sDateCreateFileHMS.split(":")[1]);
                    SS = Float.parseFloat(sDateCreateFileHMS.split(":")[2].replace("Z", ""));
                    timeFile = HH * 60 * 60 + MM * 60 + SS;
                    timeNow = this.H * 60 * 60 + this.m * 60 + this.s;
                    logger.debug("time create {}  - handle - {}", attr.creationTime(), sDateCreateFileHMS);
                    Long ii = i.toMillis();
                    Date d = new Date();
                    long dd = d.getTime();
                    Long difTime = dd - ii;
                    logger.debug("time file {} time now  {} delay {}", timeFile, timeNow, delay);
                    float timeDif = timeNow - timeFile;
//                    if ((timeNow - timeFile) >= delay) {
                    if ((difTime) >= (delay)) {
//
//                    Files.deleteIfExists(Paths.get(PATH_DIR + fileName));
                        if (Files.deleteIfExists(Paths.get(PATH_DIR + fileName))) {
                            logger.debug("удаляем файл {} ", fileName);
                        } else {
                            String msg = " Такого файла {} нет, что то не то!";
                            logger.debug(msg, fileName);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    logger.debug("{}", e.getMessage());
                }
                Main.secondsControllerClearDir = Main.getSecondsTimeNow();
            }
        }
    }

//    public TClearDir() {
//        PATH_DIR = "C:\\F\\";
//    }


    public boolean clear(String path, String fileName) throws IOException {
        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
        logger.debug("Путь удаления файла {} ", path + fileName);
        if (Files.deleteIfExists(Paths.get(path + fileName))) {
            String msg = "{} файл удален! ";
            logger.debug(msg, fileName);
            return true;
        } else {
            String msg = " Такого файла {} нет, что то не то! ";
            logger.debug(msg, fileName);
        }
        return false;
    }

}
