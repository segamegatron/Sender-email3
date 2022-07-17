package ru.sgrc.datasender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static volatile boolean connectionStateExecUpdate;
    public static volatile boolean connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable;
    public static volatile Date dateTestControllerSenderEmail;
    public static volatile Integer secondsTestControllerSenderEmail;
    public static volatile Integer secondsControllerClearDir;

    /* todo */
    private static Date dateMain;
    private static final int delayTestControllerSenderEmail = 300;
    private static String[] configClearDir;
    Config2 config;



    /* todo */
    private String [] keySenderEmail = {"oracleUrl", "oracleUser", "oraclePassword", "limitRownum", "formatFile", "dot"};
    private static final Logger logger = LoggerFactory.getLogger(
            Main.class);
    private int minut = 20;
    private ArrayList<CurrentRunThread> threads;
    public ArrayList<Thread> threads2 = new ArrayList<>(3);

    Main(Config2 config) {
        this.config = config;
    }

    /*
    todo
     */
    private String[] genConfigKey(String field, String[] keySenderEmail) {
        String[] total = new String[keySenderEmail.length];

        for (int index=0; index < keySenderEmail.length; index++) {
            //for (String key: keySenderEmail) {
            total[index] = field;
        }
        return total;
    }

    /*
      todo
       */
    private void runThread(CurrentRunThread sender, CurrentRunThread clear){

        ArrayList<CurrentRunThread> threads = new ArrayList<>();
        threads.add(sender);
        threads.add(clear);
        while (true) {
            for (CurrentRunThread currentRunThread : threads ) {
                if (currentRunThread.getSecondsTimeNow() > getSecondsTimeNow()-minut*60) {

                }
            }
        }
    }
    private void runThread(ArrayList<Thread> threads) throws SQLException, MessagingException, IOException, InterruptedException {

        boolean step = false;
        while (true) {
            if (step) {
//                Thread.sleep(60 * 2 * 1000);
            }
            step = true;
            int nowTimeDelay;
            for (Thread currentRunThread : threads ) {
                logger.debug("currentRunThread.getState() {}", currentRunThread.getState());
                logger.debug("currentRunThread.getName() {}", currentRunThread.getName());
//                boolean b = String.valueOf(currentRunThread.getState()).equals("TERMINATED")String.valueOf(currentRunThread.getState()).equals("TERMINATED");
                if (String.valueOf(currentRunThread.getState()).equals("TERMINATED")) {
                    if (currentRunThread.getName().equals("SenderEmail")){
                        threads2.remove(currentRunThread);
                        addThread(runnableControllerSenderEmail());
                        continue;
                    }
                    if (currentRunThread.getName().equals("CLearDir")){
                        threads2.remove(currentRunThread);
                        addThread(runnableControllerClearDir());
                        continue;
                    }
                }
                logger.debug("secondsTestControllerSenderEmail is ->{}<- ", secondsTestControllerSenderEmail);
                logger.debug("secondsControllerClearDir is ->{}<- ", secondsControllerClearDir);
                logger.debug("Main is {} ", getSecondsTimeNow());

                logger.debug("currentRunThread.getState().equals(\"NEW\") is {} ", String.valueOf(currentRunThread.getState()).equals("NEW"));

                if (String.valueOf(currentRunThread.getState()).equals("NEW")) {
                    logger.debug("Стартуем поток {}", currentRunThread.getName());
                    if (currentRunThread.getName().equals("ClearDir")) {
                        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
                    }
                    if (currentRunThread.getName().equals("SenderEmail")) {
                        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
                    }
                    currentRunThread.start();
//                    logger.debug("Уходим в паузу 300 сек");
//                    Thread.sleep(300000);
                    continue;
                }
                /** доработать ...
                 if (secondsTestControllerSenderEmail == null && currentRunThread.getName().equals("SenderEmail")) {
                 //                    currentRunThread.start();
                 secondsTestControllerSenderEmail = getSecondsTimeNow();
                 logger.debug("secondsTestControllerSenderEmail is null, стартуем поток {} ", currentRunThread.getName());
                 continue;
                 }

                 if (secondsControllerClearDir == null && currentRunThread.getName().equals("ClearDir")) {
                 //                    currentRunThread.start();
                 secondsControllerClearDir = getSecondsTimeNow();
                 logger.debug(" secondsControllerClearDir is null, стартуем поток {} ", secondsControllerClearDir);
                 continue;
                 } */
                if (secondsTestControllerSenderEmail != null) {
                    if (getSecondsTimeNow() - secondsTestControllerSenderEmail < 0 && currentRunThread.getName().equals("SenderEmail")) {
                        nowTimeDelay = secondsTestControllerSenderEmail - getSecondsTimeNow();
                    } else {
                        nowTimeDelay = getSecondsTimeNow() - secondsTestControllerSenderEmail;
                    }

//                    logger.debug("delayTestControllerSenderEmail {} ", delayTestControllerSenderEmail);
//                    logger.debug("nowTimeDelay {} >= {} delayTestControllerSenderEmail", nowTimeDelay, delayTestControllerSenderEmail);

//                if (false) {
                    if (nowTimeDelay >= delayTestControllerSenderEmail ){

                        if (currentRunThread.isAlive()) {
//                        threadControllerSenderEmail.isInterrupted();
//                        threadControllerSenderEmail.interrupt();
                            logger.debug("nowTimeDelay {} >= {} delayTestControllerSenderEmail", nowTimeDelay, delayTestControllerSenderEmail);

                            if (connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable) {
                                logger.debug("Возможно соединение с базой открыто connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable {}, а поток не смог закрыть соединение так как повис, это действие не обработано, " +
                                        "но сеодинение должно быть закрыто со стороны Оракла ближайшие 10 сек", connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable);

                            }
                            logger.debug("Поток  {} возможно висит", currentRunThread.getName());
                            logger.debug("Поток {} останавливаем  ", currentRunThread.getName());
                            currentRunThread.stop();

//                        threadControllerSenderEmail.stop();
                        }
//                    threadControllerSenderEmail.stop();
//                    logger.debug("currentRunThread.getState() {}", currentRunThread.getState());
//                    if (String.valueOf(currentRunThread.getState()).equals("TERMINATED")) {
////                        Thread.currentThread().stop();
//                        if (currentRunThread.getName().equals("SenderEmail")) {
//                            runnableControllerSenderEmail();
//                        }
//                        if (currentRunThread.getName().equals("ClearDir")) {
//                            runnableControllerClearDir();
//                        }
//                    }
                    }
                }


//                if (true) {//currentRunThread.getSecondsTimeNow() > getSecondsTimeNow()-minut*60) {}

            }
//            break;
        }
    }

    /*
         todo
          */
    private ArrayList<CurrentRunThread> addThread(CurrentRunThread currentRunThread) {
        threads.add(currentRunThread);
        return threads;
    }

    private void addThread(Thread currentRunThread) {
        threads2.add(currentRunThread);
    }

    private Config2 getConfig2() {
        return this.config;
    }
    private String[] getConfigSenderEmail(Main Main) {
        return new String[]  {
                Main.config.getField("oracleUrl")
                , Main.config.getField("oracleUser")
                , Main.config.getField("oraclePassword")
                , Main.config.getField("limitRownum")
                , Main.config.getField("formatFile")
                , Main.config.getField("dot")};
    }

    public static int getSecondsTimeNow() {
        return  Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()))*60
                +Integer.parseInt(new SimpleDateFormat("ss").format(Calendar.getInstance().getTime()));
    }
    private Thread runnableControllerSenderEmail() throws SQLException, MessagingException, IOException {
//        RunnableControllerSenderEmail runnableControllerSenderEmail = new RunnableControllerSenderEmail(getConfigSenderEmail(new Main(new Config2())));
        RunnableControllerSenderEmail runnableControllerSenderEmail = new RunnableControllerSenderEmail(getConfig2());
        Thread threadControllerSenderEmail = new Thread(runnableControllerSenderEmail);
        threadControllerSenderEmail.setName("SenderEmail");
        logger.debug("Создаем поток {} ", threadControllerSenderEmail.getName());

        logger.debug("Стартуем поток повторно 199 runnableControllerSenderEmail() {} ", threadControllerSenderEmail.getName());
        return threadControllerSenderEmail;
//        threadControllerSenderEmail.start();
    }
    private Thread runnableControllerClearDir() throws SQLException, MessagingException, IOException {
//        RunnableControllerSenderEmail runnableControllerSenderEmail = new RunnableControllerSenderEmail(getConfigSenderEmail(new Main(new Config2())));

//        String[] configClearDir = Main.getConfigClearDir(Main);
        RunnableControllerTClearDir runnableControllerTClearDir = new RunnableControllerTClearDir(configClearDir);
        Thread threadControllerTClearDir = new Thread(runnableControllerTClearDir);
        threadControllerTClearDir.setName("SenderEmail");
        logger.debug("Создаем поток {} ", threadControllerTClearDir.getName());
        return threadControllerTClearDir;
//        logger.debug("Стартуем поток повторно {} ", threadControllerTClearDir.getName());

//        threadControllerTClearDir.start();
    }

    private String[] getConfigClearDir(Main Main) {
        String path = Main.config.getField("pathForTempFiles");
        String delay = Main.config.getField("delay");

        return new String[]{path, delay};
    }

    /*** Добавление структуры управления потоками */

    public static void main(String[] args) throws SQLException, MessagingException, IOException, InterruptedException {

        Main Main = new Main(new Config2());
        String[] configSenderEmail = Main.getConfigSenderEmail(Main);
        configClearDir = Main.getConfigClearDir(Main);

        /**
         * Инициализция потока SenderEmail
         */
        RunnableControllerSenderEmail runnableControllerSenderEmail = new RunnableControllerSenderEmail(Main.getConfig2());
        Thread threadControllerSenderEmail = new Thread(runnableControllerSenderEmail);
        threadControllerSenderEmail.setName("SenderEmail");
        logger.debug("Создаем поток {} ", threadControllerSenderEmail.getName());

        /**
         * Инициализция потока ClearDir
         */
        RunnableControllerTClearDir runnableControllerTClearDir = new RunnableControllerTClearDir(configClearDir);
        Thread threadControllerTClearDir = new Thread(runnableControllerTClearDir);
        threadControllerTClearDir.setName("ClearDir");
        logger.debug("Создаем поток {}", threadControllerTClearDir.getName());

        /**
         * Добавление потока в коллекцию потоков
         */
        /** Отключаем времено SenderEmail */
        Main.addThread(threadControllerSenderEmail);
        /** Отключаем временно ClearDir */
         Main.addThread(threadControllerTClearDir);
         /** */

        /**
         * Запуск коллекции потоков
         */
        Main.runThread(Main.threads2);

    }


    /**
     * Работающий вариант с управлением главным потока дочернего.
     * @param args
     * @throws SQLException
     * @throws MessagingException
     * @throws IOException
     * @throws InterruptedException

    public static void main(String[] args) throws SQLException, MessagingException, IOException, InterruptedException {
    Main Main = new Main(new Config2());
    String[] configSenderEmail = Main.getConfigSenderEmail(Main);

    dateMain = new Date();
    RunnableControllerSenderEmail runnableControllerSenderEmail = new RunnableControllerSenderEmail(new Config2());
    //        runnableControllerSenderEmail.
    Thread threadControllerSenderEmail;
    threadControllerSenderEmail = new Thread(runnableControllerSenderEmail);
    threadControllerSenderEmail.setName("SenderEmail");
    logger.debug("Создаем поток {} ", threadControllerSenderEmail.getName());
    logger.debug("Стартуем поток {} ", threadControllerSenderEmail.getName());
    threadControllerSenderEmail.start();
    System.out.println(threadControllerSenderEmail.isAlive());
    logger.debug("runnableControllerSenderEmail.getSecondsTimeNow() {}", runnableControllerSenderEmail.getSecondsTimeNow());

    while (true) {

    for (double step = 0; step < 100000000; step+=0.1) {
    //            logger.debug(String.valueOf(threadControllerSenderEmail.getState()));
    //            logger.debug(String.valueOf(threadControllerSenderEmail.isAlive()));
    //            logger.debug(" dateMain {} ", dateMain);
    //            logger.debug(" runnableControllerSenderEmail.dateRunnableControllerSenderEmail {} ", runnableControllerSenderEmail.dateRunnableControllerSenderEmail);
    }
    dateMain = new Date();
    int secondsMain = Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()))*60
    +Integer.parseInt(new SimpleDateFormat("ss").format(Calendar.getInstance().getTime()));

    logger.debug("Текущее время исполнения secondsMain {} ", secondsMain);
    logger.debug("Текущее время исполнения secondsTestControllerSenderEmail {} ", secondsTestControllerSenderEmail);
    logger.debug("Текущее время исполнения dateMain {} ", dateMain);
    //            logger.debug(" runnableControllerSenderEmail.dateRunnableControllerSenderEmail {} ", runnableControllerSenderEmail.dateRunnableControllerSenderEmail);
    logger.debug("Текущее время исполнения dateTestControllerSenderEmail {} ", dateTestControllerSenderEmail);
    logger.debug("Статус потока {} {} ", threadControllerSenderEmail.getName(), threadControllerSenderEmail.getState());
    logger.debug("Статус сесссии {} с БД Oracle потока  {} ",  connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable, threadControllerSenderEmail.getName());

    //            Thread.sleep(3000);
    int nowTimeDelay;
    //            if (secondsTestControllerSenderEmail == null) {
    //                secondsTestControllerSenderEmail = 300;
    //            }
    if (secondsMain - secondsTestControllerSenderEmail < 0) {
    nowTimeDelay = secondsTestControllerSenderEmail - secondsMain;
    } else {
    nowTimeDelay = secondsMain - secondsTestControllerSenderEmail;
    }
    if (nowTimeDelay >= delayTestControllerSenderEmail ){
    if (threadControllerSenderEmail.isAlive()) {
    //                        threadControllerSenderEmail.isInterrupted();
    //                        threadControllerSenderEmail.interrupt();
    if (connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable) {

    }
    //                    threadControllerSenderEmail.stop();
    //                        threadControllerSenderEmail.stop();
    }
    //                    threadControllerSenderEmail.stop();
    String a = String.valueOf(threadControllerSenderEmail.getState());
    if (String.valueOf(threadControllerSenderEmail.getState()).equals("TERMINATED")) {
    //                        Thread.currentThread().stop();
    //                    runnableControllerSenderEmail = new RunnableControllerSenderEmail(configSenderEmail);
    runnableControllerSenderEmail = new RunnableControllerSenderEmail(new Config2());
    threadControllerSenderEmail = new Thread(runnableControllerSenderEmail);
    threadControllerSenderEmail.setName("SenderEmail");
    logger.debug("Создаем поток повторно {} ", threadControllerSenderEmail.getName());
    threadControllerSenderEmail.start();
    }
    //                Thread.currentThread().stop();
    }
    }
    //        if (dateMain )
    //        System.out.println();
    //        System.out.println(runnableControllerSenderEmail.dateRunnableControllerSenderEmail);

    //        for (String key:configSenderEmail) {
    ////            logger.debug("Key {} ", key);
    //        }
    }

     */

}
