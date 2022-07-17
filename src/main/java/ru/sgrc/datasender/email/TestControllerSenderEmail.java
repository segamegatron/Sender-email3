package ru.sgrc.datasender.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sgrc.datasender.*;
import ru.sgrc.datasender.email.EmailSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TestControllerSenderEmail {
    private final Logger logger = LoggerFactory.getLogger(
            TestControllerSenderEmail.class);
    private Config2 config2;
    protected int[] state = {0, 1, 2, -2};
    //    protected static int state = 1;
    private String[] response;
    Runnable taskControllerSenderEmail;
    public volatile Date dateTestControllerSenderEmail;

    public TestControllerSenderEmail(Config2 config) {
        this.config2 = config;
    }

    public TestControllerSenderEmail() {

    }


    /**
     * @throws SQLException
     * @throws MessagingException
     * @throws IOException
     */
//    public TestControllerSenderEmail () throws SQLException, MessagingException, IOException {
////        if (flag) {
////        taskControllerSenderEmail = () -> {
////
////            Config2 config2 = new Config2();
////            String format = config2.getField("formatFile");
////            String dot = config2.getField("dot");
////
////            /**
////             //         * Создаем модель общение с БД
////             //         */
////            Model model = new Model();
////            model.limitRownum = config2.getField("limitRownum");
////            model.oracleUrl = config2.getField("oracleUrl");
////            model.oracleUser = config2.getField("oracleUser");
////            model.oraclePassword = config2.getField("oraclePassword");
////            System.out.println("model.oracleUrl -> " + model.oracleUrl);
////            System.out.println("model.oracleUser -> " + model.oracleUser);
////            System.out.println("model.oraclePassword -> " + model.oraclePassword);
//////        System.out.println(model.oracleUrl);
////            logger.debug("Создаем обьект  Model() общение с Ora БД");
////
////            /**
////             * ограничение количество строк получаемых в запросе
////             */
////            model.limitRownum = config2.getField("limitRownum");
////            logger.debug("определяем rownum максимальное колво записей в запросе {}", model.limitRownum);
//////        logger.debug(" ", );
////
////            try {
////                model.execSQLCNT(model.QUERY_MAILLOG_CNT, model.COLUMN_CNT);
////            } catch (SQLException e) {
////                throw new RuntimeException(e);
////            }
////
////            System.out.println("Не более чем " + model.limitRownum + " строк(и) ");
////            /**
////             * Работа с базой раскоментировать после логгирования и рефактора!
////             * */
////
////            /**
////             * Если записей больше нуля, то продолжаем
////             */
////
////            /**
////             * Расскоментировать после проверки config.ini
////             */
////            if (model.execSQLQUERY_MAILLOG_DATA(model.QUERY_MAILLOG_DATA, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
////                    model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID)) {
////                logger.debug("Выполняем execSQLQUERY_MAILLOG_DATA {}", model.COLUMN_EMAIL);
////
////                String[] responseAll = model.response.split(model.delimiterEnd);
////
////                if (!model.response.equals("")) {
////
////                    for (int index = model.index; index < responseAll.length; index++) {
////
////                        System.out.print(" - " + responseAll[index]);
////                        response = responseAll[index].split(model.delimiter);
////                        String urlForPdf = response[4];
////                        String nameFile = response[5];
////                        String mail_id = response[5];
////                        GoToURL pdf = new GoToURL(urlForPdf, nameFile + dot + format, config2.getField("pathForTempFiles"));
////
////                        if (pdf.writeToFile) {
////                            System.out.println("Платежный документ создан!");
////                            logger.debug("Платежный документ создан!");
////
////                            if (model.execSQLUpdate(model.QUERY_UPDATE, state[1], mail_id)) {
////                                logger.debug("в таблице maillog state изменен в 1 , отправляется!");
////                                System.out.println("в таблице maillog state изменен в 1 , отправляется!");
////                            }
////                            EmailSender emailSender = new EmailSender(response, model.delimiter, format);
////                            System.out.println("config2.getField(pathForTempFiles) " + config2.getField("pathForTempFiles"));
////                            emailSender.setField("pathForTempFiles", config2.getField("pathForTempFiles"));
////
////                            System.out.println("emailSender.pathForTempFiles " + emailSender.pathForTempFiles);
////
////                            if (emailSender.setField("emailPassword", config2.getField("emailPassword"))
////                                    && emailSender.setField("fromMail", config2.getField("fromMail"))
////                                // && emailSender.setField("mailProperties", config2.getField("mailProperties"))
////                            ) {
////                                System.out.println("emailSender.passwordMail -> " + emailSender.emailPassword);
////                                try {
////                                    if (emailSender.sendEmail()) {
////                                        logger.debug("Email отправлено! на {}", mail_id);
////                                        System.out.println("Email отправлено! на " + mail_id);
////
////                                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[2], mail_id)) {
////                                            logger.debug("В таблице maillog state изменен в 2 , уже отправленное!");
////                                            System.out.println("В таблице maillog state изменен в 2 , уже отправленное!");
////                                        }
////                                    } else {
////                                        logger.debug("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
////                                        System.out.println("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
////                                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
////                                            logger.debug("В таблице maillog state изменен в -2 , письмо не отправлено!");
////                                            System.out.println("В таблице maillog state изменен в -2 , письмо не отправлено!");
////                                        }
////                                    }
////                                } catch (IOException e) {
////                                    throw new RuntimeException(e);
////                                } catch (MessagingException e) {
////                                    throw new RuntimeException(e);
////                                }
////                            }
////
////                        } else {
////                            logger.debug("Ошибка создания файла ПД pdf");
////                            System.out.println("Ошибка создания файла ПД pdf");
////                            if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
////                                logger.debug("В таблице maillog state изменен в -2 , не отправлено! ");
////                                System.out.println("В таблице maillog state изменен в -2 , не отправлено! ");
////                            }
////                        }
////
////                    }
////                }
////
////            }
////
////
////        };
////        }
//    }

//    public ControllerSenderEmail() {
//
//    }
    public int getSecondsTimeNow() {
        return Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime())) * 60
                + Integer.parseInt(new SimpleDateFormat("ss").format(Calendar.getInstance().getTime()));
    }

    /**
     * @throws SQLException
     * @throws MessagingException
     * @throws IOException
     */
    public void controllerSenderEmail() throws InterruptedException {

        Main.secondsTestControllerSenderEmail = Main.getSecondsTimeNow();
//        String[] config = this.config;
//        String formatFile = config[0];
//        Config2 config2 = new Config2();
        String format = config2.getField("formatFile");
        String dot = config2.getField("dot");
        String pathForTempFiles = config2.getField("pathForTempFiles");

//        String format = config2.getField("formatFile");
//        String dot = config2.getField("dot");


        /**
         * Создаем модель общение с БД
         * */
        Model model = new Model();
        model.limitRownum = config2.getField("limitRownum");
        model.oracleUrl = config2.getField("oracleUrl");
        model.oracleUser = config2.getField("oracleUser");
        model.oraclePassword = config2.getField("oraclePassword");
//        System.out.println("model.oracleUrl -> " + model.oracleUrl);
//        System.out.println("model.oracleUser -> " + model.oracleUser);
//        System.out.println("model.oraclePassword -> " + model.oraclePassword);
//        System.out.println(model.oracleUrl);
        logger.debug("Создаем обьект  Model() общение с Ora БД");

        /**
         * ограничение количество строк получаемых в запросе
         */
        model.limitRownum = config2.getField("limitRownum");
//        logger.debug("определяем rownum максимальное колво записей в запросе {}", model.limitRownum);
//        logger.debug(" ", );

//            try {
//                model.execSQLCNT(model.QUERY_MAILLOG_CNT, model.COLUMN_CNT);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }

//        System.out.println("Не более чем " + model.limitRownum + " строк(и) ");

        /**
         * Работа с базой раскоментировать после логгирования и рефактора!
         * */

        /**
         * Если записей больше нуля, то продолжаем
         */

        /**
         * Расскоментировать после проверки Closeable()

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
         System.out.println("config2.getField(pathForTempFiles) " + config2.getField("pathForTempFiles"));
         emailSender.setField("pathForTempFiles", config2.getField("pathForTempFiles"));

         System.out.println("emailSender.pathForTempFiles " + emailSender.pathForTempFiles);

         if (emailSender.setField("emailPassword", config2.getField("emailPassword"))
         && emailSender.setField("fromMail", config2.getField("fromMail"))
         // && emailSender.setField("mailProperties", config2.getField("mailProperties"))
         ) {
         System.out.println("emailSender.passwordMail -> " + emailSender.emailPassword);
         try {
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
         } catch (IOException e) {
         throw new RuntimeException(e);
         } catch (MessagingException e) {
         throw new RuntimeException(e);
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
        int cnt = 0;
        while (true) {


//            Thread.sleep(3000);
//            logger.debug("Выполняем model.execSQLQUERY_MAILLOG_DATA_Closeable {}", model.COLUMN_EMAIL);
           /* if (model.execSQLQUERY_MAILLOG_DATA_Closeable(model.QUERY_MAILLOG_DATA, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
                    model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID)) {*/

            if (model.execSQLQUERY_MAILLOG_DATA_Closeable(model.QUERY_MAILLOG_MAILACCOUNT)) {
                     /*, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
                    model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID, model.COLUMN_ACC_ID, model.COLUMN_EMAIL_SENDER, model.COLUMN_SENDER, model.COLUMN_LOGIN, model.COLUMN_PASSW,
                     model.COLUMN_USESSL, model.COLUMN_SERVER, model.COLUMN_SERVERPORT) ) {*/
                if ((model.responseRow2 != null) || (model.responseRow2.isEmpty())) {
//                    logger.debug("этот {}", model.responseRow2);
                }

                for (String[] cols : model.responseRow2) {
                    for (int indx = 0; indx < model.responseRow2.size(); indx++) {
                        logger.debug(" index {} value {}", indx, cols);
                    }
                }

//                    logger.debug("Выполняем execSQLQUERY_MAILLOG_DATA {}", model.COLUMN_EMAIL);

                //String[] responseAll = model.response.split(model.delimiterEnd);
//                    ArrayList<String> responseRow = model.responseRow;
//                    for (int step = 0; step < responseRow.size(); step = step + 6) {
//                        String urlForPdf = responseRow.get(4 + step);
//                        String nameFile = responseRow.get(5 + step);
//                        String mail_id = responseRow.get(5 + step);

//                        dateTestControllerSenderEmail = new Date();
//                        logger.debug("Текущее время исполнения dateTestControllerSenderEmail {} ", dateTestControllerSenderEmail);

//                        GoToURL pdf = new GoToURL(urlForPdf, nameFile + dot + format, config2.getField("pathForTempFiles"));


                ArrayList<String[]> responseRow2 = model.responseRow2;
                for (String[] row : responseRow2) {
                    String nameFile = row[5] + dot + format;
                    String mail_id = row[5];
                    String urlForPdf = row[4];
                    GoToURL pdf = new GoToURL(urlForPdf, nameFile, config2.getField("pathForTempFiles"));
                    logger.debug("name file {} url for pdf {} ", nameFile, urlForPdf);
                    dateTestControllerSenderEmail = new Date();
                    logger.debug("Текущее время исполнения dateTestControllerSenderEmail {} ", dateTestControllerSenderEmail);
                    if (pdf.writeToFile) {
                        System.out.println("Платежный документ создан!");
                        logger.debug("Платежный документ создан!");

                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[1], mail_id)) {
                            logger.debug("в таблице maillog state изменен в 1 , отправляется!");
                            System.out.println("в таблице maillog state изменен в 1 , отправляется!");
                        }
                        response = row;

                        for (String respon:response) {
                            logger.debug("response for EmailSender {}", respon);
//                            Thread.sleep(500);
                        }
                        String toEmail = response[0];
                        String subject = response[2];
                        String text = response[3];
                        String file = response[5];
                        String fromEmail = response[9];
                        String emailPassword = response[10];
                        String host = response[12];
                        String port = response[13];
                        String[] configEmailSender = {toEmail, subject, text, file, fromEmail, host, port, format, pathForTempFiles, emailPassword};
//                        EmailSender emailSender = new EmailSender(response, format);
                        for (String respon:configEmailSender) {
                            logger.debug("configEmailSender for EmailSender {}", respon);
                        }
                        EmailSender emailSender = new EmailSender(toEmail, subject, text, file, fromEmail, host, port, format, pathForTempFiles, emailPassword);

                        emailSender.setField("pathForTempFiles", config2.getField("pathForTempFiles"));
                        if (emailSender.setField("emailPassword", config2.getField("emailPassword"))
                                && emailSender.setField("fromMail", config2.getField("fromMail"))
                        ) {
                            System.out.println("emailSender.passwordMail -> " + emailSender.emailPassword);
                            try {
                                if (emailSender.sendEmail()) {
                                    logger.debug("Email отправлено! на {}", toEmail);
                                    if (model.execSQLUpdate(model.QUERY_UPDATE, state[2], mail_id)) {
                                        logger.debug("В таблице maillog state изменен в 2 , уже отправленное!");
                                    }
                                } else {
                                    logger.debug("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
                                    if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
                                        logger.debug("В таблице maillog state изменен в -2 , письмо не отправлено!");
                                    }
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (MessagingException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    } else {
                        logger.debug("Ошибка создания файла ПД pdf");
                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
                            logger.debug("В таблице maillog state изменен в -2 , не отправлено! ");
                        }
                    }

                }


//                logger.debug("Текущее время исполнения dateTestControllerSenderEmail {} ", dateTestControllerSenderEmail);

                Main.dateTestControllerSenderEmail = new Date();

                Main.secondsTestControllerSenderEmail = Main.getSecondsTimeNow();
//                logger.debug("Main.secondsTestControllerSenderEmail из TestControllerSenderEmail получено методом Main.getSecondsTimeNow() {} ", Main.getSecondsTimeNow());
//                logger.debug("Main.secondsTestControllerSenderEmail из TestControllerSenderEmail получено методом {} getSecondsTimeNow()", getSecondsTimeNow());
            }
//            Thread.sleep(1000);
        }
/*
                Thread.currentThread().wait(3000);

                logger.debug("Текущее время исполнения dateTestControllerSenderEmail {} ", Main.dateTestControllerSenderEmail);
                cnt +=1;
                logger.debug("Принудительно завершаем поток Thread.currentThread().stop()");
                Thread.currentThread().stop();
                Thread.sleep(10000);

*/

    }


//        };

}


