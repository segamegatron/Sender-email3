package ru.sgrc.datasender.cls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class ClearDir {
    public String PATH_DIR="";
    private int delay;
    //    File dir = new File(PATH_DIR);
//    final File dir;
    String formatDate = "YYYYMMdd";
    String formateDateFull = "yyyyMMddHHmmss";
    Integer currentDate = Integer.parseInt(new SimpleDateFormat(formatDate).format(Calendar.getInstance().getTime()));
    Integer H = Integer.parseInt(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()));
    Integer m = Integer.parseInt(new SimpleDateFormat("mm").format(Calendar.getInstance().getTime()));
    Integer s = Integer.parseInt(new SimpleDateFormat("ss").format(Calendar.getInstance().getTime()));

    Path file;
    BasicFileAttributes attr;

    String sDateCreateFile;
    Logger logger = LoggerFactory.getLogger(ClearDir.class);
    private int local = 3;


    public ClearDir() {

    }

    public ClearDir(String path, String[] nameFileToDelete) throws IOException {
        this.PATH_DIR = path;
        for (String nameFile : nameFileToDelete) {
            logger.debug(nameFile);
            logger.debug("Подготвка к удалению файла/ов {} ", nameFile);
            clear(path, nameFile);
        }

    }

    public ClearDir(String PATH_DIR, int delay) {
        this.delay = delay;
        this.PATH_DIR = PATH_DIR;
        File dir = new File(this.PATH_DIR);

        for (String fileName : Objects.requireNonNull(dir.list())) {

            try {
                logger.debug(PATH_DIR+fileName);
                file = Paths.get(PATH_DIR + fileName);
                attr = Files.readAttributes(file, BasicFileAttributes.class);

                sDateCreateFile = String.valueOf(attr.creationTime())
                        .replace("-", "")
                        .replace("T", "")
                        .replace(":", "")
                        .replace("Z", "");
                Integer sDateCreateFileHMSFree = Integer.parseInt(String.valueOf(attr.creationTime())
                        .replace("-", "")
                        .split("T")[0]);
                System.out.println("sDateCreateFileHMSFree " + sDateCreateFileHMSFree);
                System.out.println("currentDate " + currentDate);
                System.out.println(PATH_DIR + fileName);
//                if (compareDate()) {}
                if ((Integer.parseInt(String.valueOf(currentDate)) - Integer.parseInt(String.valueOf(sDateCreateFileHMSFree))) >= delay) {
//                    Files.deleteIfExists(Paths.get(PATH_DIR + fileName));
                    if (Files.deleteIfExists(Paths.get(PATH_DIR + fileName))) {
                        logger.debug("удаляем файл {} ", fileName);
                        System.out.println(fileName + " file is deleted!");
                    } else {
                        String msg = " Такого файла {} нет, что то не то!";
                        logger.debug(msg, fileName);
                        System.out.println(fileName + msg);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void tClearDir(String PATH_DIR, int delay) {
        this.delay = delay;
        File dir = new File(PATH_DIR);

        for (String fileName : Objects.requireNonNull(dir.list())) {

            try {
                logger.debug(PATH_DIR+fileName);
                file = Paths.get(PATH_DIR + fileName);
                attr = Files.readAttributes(file, BasicFileAttributes.class);
                logger.debug("time create {}  - file - {}", attr.creationTime(), fileName) ;

                String sDateCreateFileHMS = String.valueOf(attr.creationTime()).split("T")[1];
                Integer HH = Integer.parseInt(sDateCreateFileHMS.split(":")[0]) + this.local;
                Integer MM = Integer.parseInt(sDateCreateFileHMS.split(":")[1]);
                Float SS = Float.parseFloat(sDateCreateFileHMS.split(":")[2].replace("Z", ""));
                System.out.println("time file " + HH + " " + MM + " " + SS);
                System.out.println("time now  " + this.H + " " + this.m + " " + this.s);
                float timeFile = HH*60*60+MM*60+SS;
                float timeNow  = this.H*60*60+this.m*60+this.s;
                System.out.println("time file " + timeFile);
                System.out.println("time now  " + timeNow);
                System.out.println(timeNow - timeFile);
                logger.debug("time create {}  - handle - {}", attr.creationTime(), sDateCreateFileHMS) ;

                System.out.println(PATH_DIR + fileName);

                if ((timeNow - timeFile) >= delay) {
                    if (Files.deleteIfExists(Paths.get(PATH_DIR + fileName))) {
                        logger.debug("удаляем файл {} ", fileName);
                        System.out.println(fileName + " file is deleted!");
                    } else {
                        String msg = " Такого файла {} нет, что то не то!";
                        logger.debug(msg, fileName);
                        System.out.println(fileName + msg);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean clear(String path, String fileName) throws IOException {
        logger.debug("Путь удаления файла {} ", path + fileName );
        if (Files.deleteIfExists(Paths.get(path + fileName ))) {
            String msg = "{} файл удален! ";
            logger.debug(msg, fileName);
            System.out.println(fileName + " файл удален!");
            return true;
        } else {
            String msg = " Такого файла {} нет, что то не то! ";
            logger.debug(msg, fileName);
            System.out.println(  msg + fileName);
        }
        return false;
    }

    public boolean compareDate(Date currentDate, Date bDate, Date fDate) {
        if ((currentDate.after(bDate)) && (currentDate.before(fDate))) {
            return true;
        }
        return false;
    }
}


