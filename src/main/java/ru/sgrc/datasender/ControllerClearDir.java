package ru.sgrc.datasender;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sgrc.datasender.cls.ClearDir;
import ru.sgrc.datasender.email.EmailSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;


public class ControllerClearDir {
    private static final Logger logger = LoggerFactory.getLogger(
            ControllerClearDir.class);
    private static String[] response;

    /**
     *
     */
    protected static String pathDir = "C:\\PDF\\";


    /**
     * формат создаваемого файла, который читаем с сервера
     */
    public static String format = "txt";
    protected String objFormat = "";
    public static String dot = ".";

    /**
     * Статус отправки почты 0 еще нет, 1 отправляется, 2 успешно отправлено, -2 ошибка отправки
     */
    protected static int[] state = {0, 1, 2, -2};
//    protected static int state = 1;

    protected static String url = "";
    Runnable taskClearDir;

    /**
     * - запрос данных с БД (ФИО, Тема, Контент(тело письма) Email, Файл ПД (pdf))
     * - вызов метода Model.prepareToSendEmail( data[] ФИО, Тема, Контент(тело письма) Email, Файл ПД (pdf)).
     * - Проверка состояния файлов checkFiles() (количество, )
     * - Удаление методом clear() файлов(платежных документов), если дата отличается более, чем два дня от тек.даты.
     *
     *  pathDir путь к файлам ПД которые надо отправить , а затем удалить.
     */

    ControllerClearDir() {
        /**
        taskClearDir = () -> {
            try {
                int secToWait = 1000 * 3;
                logger.debug("Поток ожидает {} сек ", secToWait);
                Thread.currentThread().sleep(secToWait);

                /**
                 * Начало выполнения очистки
                 */
                Main.secondsControllerClearDir = Main.getSecondsTimeNow();

                Config2 config2 = new Config2();
                format = config2.getField("formatFile");
                dot = config2.getField("dot");
                System.out.println("Разбудили  поток удаления файлов");
//                logger.debug("Разбудили  поток удаления файлов");
//                logger.debug("Создаем обьект ClearDir для Очищения от ПД");
                pathDir = config2.getField("pathForTempFiles");
                int delay = Integer.parseInt(config2.getField("delay"));
                System.out.println("pathDir -> " + pathDir);
                System.out.println("delay -> " + delay);
                ClearDir cd = new ClearDir();
                cd.tClearDir(pathDir, delay);

                /**
                 * Создаем модель общение с БД
                 */
                Model model = new Model();
                model.limitRownum = config2.getField("limitRownum");
                model.oracleUrl = config2.getField("oracleUrl");
                model.oracleUser = config2.getField("oracleUser");
                model.oraclePassword = config2.getField("oraclePassword");
                System.out.println("model.oracleUrl -> "+model.oracleUrl);
                System.out.println("model.oracleUser -> " + model.oracleUser);
                System.out.println("model.oraclePassword -> " + model.oraclePassword);
//        System.out.println(model.oracleUrl);
                logger.debug("Создаем обьект  Model() общение с Ora БД");

                /**
                 * ограничение количество строк получаемых в запросе
                 */
                model.limitRownum = config2.getField("limitRownum");
                logger.debug("определяем rownum максимальное колво записей в запросе {}", model.limitRownum);
                /**
                 * Работа с базой раскоментировать после логгирования и рефактора!
                 * */

                /**
                 * Если записей больше нуля, то продолжаем
                 */

                /**
                 * Расскоментировать после проверки config.ini

                if (model.execSQLQUERY_MAILLOG_DATA(model.QUERY_MAILLOG_DATA, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
                        model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID)) {
                    logger.debug("Выполняем execSQLQUERY_MAILLOG_DATA {}", model.COLUMN_EMAIL);

                    String[] responseAll = model.response.split(model.delimiterEnd);

                    if (!model.response.equals("")) {

                        for (int index = model.index; index < responseAll.length; index++) {

                            System.out.print(" - " + responseAll[index]);
                            response = responseAll[index].split(model.delimiter);
                            String urlForPdf = response[4];
                            String nameFile = response[5];
                            String mail_id = response[5];
                            GoToURL pdf = new GoToURL(urlForPdf, nameFile + dot + format, config2.getField("pathForTempFiles"));

                            if (pdf.writeToFile) {
                                System.out.println("Платежный документ создан!");
                                logger.debug("Платежный документ создан!");

                                if (model.execSQLUpdate(model.QUERY_UPDATE, state[1], mail_id)) {
                                    logger.debug("в таблице maillog state изменен в 1 , отправляется!");
                                    System.out.println("в таблице maillog state изменен в 1 , отправляется!");
                                }
                                EmailSender emailSender = new EmailSender(response, model.delimiter, format);
                                System.out.println("config2.getField(pathForTempFiles) "+config2.getField("pathForTempFiles"));
                                emailSender.setField("pathForTempFiles", config2.getField("pathForTempFiles"));

                                System.out.println("emailSender.pathForTempFiles "+emailSender.pathForTempFiles);

                                if ( emailSender.setField("emailPassword", config2.getField("emailPassword"))
                                        && emailSender.setField("fromMail", config2.getField("fromMail"))
                                    // && emailSender.setField("mailProperties", config2.getField("mailProperties"))
                                ) {
                                    System.out.println("emailSender.passwordMail -> "+emailSender.emailPassword);
                                    if (emailSender.sendEmail()) {
                                        logger.debug("Email отправлено! на {}", mail_id);
                                        System.out.println("Email отправлено! на " + mail_id);

                                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[2], mail_id)) {
                                            logger.debug("В таблице maillog state изменен в 2 , уже отправленное!");
                                            System.out.println("В таблице maillog state изменен в 2 , уже отправленное!");
                                        }
                                    } else {
                                        logger.debug("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
                                        System.out.println("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
                                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
                                            logger.debug("В таблице maillog state изменен в -2 , письмо не отправлено!");
                                            System.out.println("В таблице maillog state изменен в -2 , письмо не отправлено!");
                                        }
                                    }
                                }

                            } else {
                                logger.debug("Ошибка создания файла ПД pdf");
                                System.out.println("Ошибка создания файла ПД pdf");
                                if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
                                    logger.debug("В таблице maillog state изменен в -2 , не отправлено! ");
                                    System.out.println("В таблице maillog state изменен в -2 , не отправлено! ");
                                }
                            }

                        }
                    }

                }
                 */

/**
            } catch (InterruptedException | IOException | MessagingException e) {
                e.printStackTrace();
            }
        };*/
        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
    }


    public void EXCEPTION_main(String[] args) throws  IOException, javax.mail.MessagingException, SQLException {


        /**
         * Тестируем config.ini
         */
//        Config2 config2 = new Config2();
//        format = config2.getField("formatFile");
//        dot = config2.getField("dot");


//        System.out.println("format " + format);



        /**
         * Тестовая часть по логгированию, потом отключить, можно удалить
         * -------------------------------------------------------------------
         *

         User user = new User();
         user.setName("Anakin");
         user.setLastName("Skywalker");

         userLogger.info(user.showMeMessage());
         userLogger.info(user.giveMeASign());

         rootLogger.info("Root Logger: "  + user.showMeMessage());

         debug
         if (rootLogger.isDebugEnabled()) {
         rootLogger.debug("RootLogger: In debug message");
         userLogger.debug("UserLogger in debug");
         }

         try {
         User userNull = new User();
         userNull.getName().toString();
         } catch (NullPointerException ex) {
         userLogger.error("error message: " + ex.getMessage());
         userLogger.fatal("fatal error message: " + ex.getMessage());
         }


         /**
         * Конец Тестовой части по логгированию
         * ----------------------------------------------------------------------
         */


        /**
         * Создать группу потоков, включить два отдельных потока:
         * 1 - подготовка и отправка письма,
         * 2 - чистка директории созданных файлов(ПД платежных документов)
         */

//
//        /**
//         * Создаем модель общение с БД
//         */
//        Model model = new Model();
//        model.limitRownum = config2.getField("limitRownum");
//        model.oracleUrl = config2.getField("oracleUrl");
//        model.oracleUser = config2.getField("oracleUser");
//        model.oraclePassword = config2.getField("oraclePassword");
//        System.out.println("model.oracleUrl -> "+model.oracleUrl);
//        System.out.println("model.oracleUser -> " + model.oracleUser);
//        System.out.println("model.oraclePassword -> " + model.oraclePassword);
////        System.out.println(model.oracleUrl);
//        logger.debug("Создаем обьект  Model() общение с Ora БД");
//
//        /**
//         * ограничение количество строк получаемых в запросе
//         */
//        model.limitRownum = config2.getField("limitRownum");
//        logger.debug("определяем rownum максимальное колво записей в запросе {}", model.limitRownum);
//        logger.debug(" ", );

//        model.execSQLCNT(model.QUERY_MAILLOG_CNT, model.COLUMN_CNT);

//        System.out.println("Не более чем " + model.limit_rownum + " строк(и) ");


        /**
         * Тестовая проверка выполнения SQL запроса с учтеными фильтрами и получения результата ввиде строки
         */
        /**
         if ( model.execSQLTEST(model.QUERY_TEST, model.COLUMN_N_LIC, model.COLUMN_SURNAME) ) {
         response = model.response.split(model.delimiter);
         //            System.out.println("second " + model.limit_rownum);
         //            System.out.print(response[1]);
         //            for (int index = model.index; index < response.length; index++) {
         //
         //                //new EmailSender(response[index]);
         //            }
         for (String row:  response) {
         //                System.out.println("column");
         System.out.println("From Controller -> " + row);
         //                new EmailSender(column);
         }
         }
         */

//        /**
//         * Работа с базой раскоментировать после логгирования и рефактора!
//         * */
//
//        /**
//         * Если записей больше нуля, то продолжаем
//         */
//
//        /**
//         * Расскоментировать после проверки config.ini
//         */
//        if (model.execSQLQUERY_MAILLOG_DATA(model.QUERY_MAILLOG_DATA, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
//                model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID)) {
//            logger.debug("Выполняем execSQLQUERY_MAILLOG_DATA {}", model.COLUMN_EMAIL);
//
//            String[] responseAll = model.response.split(model.delimiterEnd);
//
//            if (!model.response.equals("")) {
//
//                for (int index = model.index; index < responseAll.length; index++) {
//
//                    System.out.print(" - " + responseAll[index]);
//                    response = responseAll[index].split(model.delimiter);
//                    String urlForPdf = response[4];
//                    String nameFile = response[5];
//                    String mail_id = response[5];
//                    GoToURL pdf = new GoToURL(urlForPdf, nameFile + dot + format, config2.getField("pathForTempFiles"));
//
//                    if (pdf.writeToFile) {
//                        System.out.println("Платежный документ создан!");
//                        logger.debug("Платежный документ создан!");
//
//                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[1], mail_id)) {
//                            logger.debug("в таблице maillog state изменен в 1 , отправляется!");
//                            System.out.println("в таблице maillog state изменен в 1 , отправляется!");
//                        }
//                        EmailSender emailSender = new EmailSender(response, model.delimiter, format);
//                        System.out.println("config2.getField(pathForTempFiles) "+config2.getField("pathForTempFiles"));
//                        emailSender.setField("pathForTempFiles", config2.getField("pathForTempFiles"));
//
//                        System.out.println("emailSender.pathForTempFiles "+emailSender.pathForTempFiles);
//
//                        if ( emailSender.setField("emailPassword", config2.getField("emailPassword"))
//                                && emailSender.setField("fromMail", config2.getField("fromMail"))
//                            // && emailSender.setField("mailProperties", config2.getField("mailProperties"))
//                        ) {
//                            System.out.println("emailSender.passwordMail -> "+emailSender.emailPassword);
//                            if (emailSender.sendEmail()) {
//                                logger.debug("Email отправлено! на {}", mail_id);
//                                System.out.println("Email отправлено! на " + mail_id);
//
//                                if (model.execSQLUpdate(model.QUERY_UPDATE, state[2], mail_id)) {
//                                    logger.debug("В таблице maillog state изменен в 2 , уже отправленное!");
//                                    System.out.println("В таблице maillog state изменен в 2 , уже отправленное!");
//                                }
//                            } else {
//                                logger.debug("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
//                                System.out.println("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
//                                if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
//                                    logger.debug("В таблице maillog state изменен в -2 , письмо не отправлено!");
//                                    System.out.println("В таблице maillog state изменен в -2 , письмо не отправлено!");
//                                }
//                            }
//                        }
//
//                    } else {
//                        logger.debug("Ошибка создания файла ПД pdf");
//                        System.out.println("Ошибка создания файла ПД pdf");
//                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
//                            logger.debug("В таблице maillog state изменен в -2 , не отправлено! ");
//                            System.out.println("В таблице maillog state изменен в -2 , не отправлено! ");
//                        }
//                    }
//
//                }
//            }
//
//        }
//

        /**
         * Создать поток и стартануть чистку директории
         */

//        this.taskClearDir = () -> {
//            try {
//                int secToWait = 1000 * 3;
//                logger.debug("Поток ожидает {} сек ", secToWait);
//                Thread.currentThread().sleep(secToWait);
//
//                /**
//                 * Начало выполнения очистки
//                 */
//                System.out.println("Разбудили  поток удаления файлов");
//                logger.debug("Разбудили  поток удаления файлов");
//                logger.debug("Создаем обьект ClearDir для Очищения от ПД");
//                pathDir = config2.getField("pathForTempFiles");
//                int delay = Integer.parseInt(config2.getField("delay"));
//                System.out.println("pathDir -> " + pathDir);
//                System.out.println("delay -> " + delay);
//                ClearDir cd = new ClearDir();
//                cd.tClearDir(pathDir, delay);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
//        Thread threadClearDir = new Thread(taskClearDir);
//        threadClearDir.start();
    }

//
//        if (!model.result.equals("-1")) {
//            if (!model.result.equals("0")) {
//                /**
//                 * Есть данные на отправку
//                 */
//
//            } else {
//                /**
//                 * Нет даных, тайм аут, таблица maillog не содержит данных
//                 */
//            }
//
//        } else {
//            /**
//             * Не предвиденная ошибка
//             */
//        }

//    }


}
