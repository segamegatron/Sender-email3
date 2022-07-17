package ru.sgrc.datasender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sgrc.datasender.email.EmailSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerSenderEmail {
    private static final Logger logger = LoggerFactory.getLogger(
            ControllerSenderEmail.class);
    protected static int[] state = {0, 1, 2, -2};
    //    protected static int state = 1;
    private String[] response;
    Runnable taskControllerSenderEmail;

    /**
     *
     * @throws SQLException
     * @throws MessagingException
     * @throws IOException
     */
    ControllerSenderEmail() throws SQLException, MessagingException, IOException {
//        if (flag) {
//            taskControllerSenderEmail = () -> {

                Config2 config2 = new Config2();
                String format = config2.getField("formatFile");
                String dot = config2.getField("dot");

                /**
                 //         * Создаем модель общение с БД
                 //         */
                Model model = new Model();
                model.limitRownum = config2.getField("limitRownum");
                model.oracleUrl = config2.getField("oracleUrl");
                model.oracleUser = config2.getField("oracleUser");
                model.oraclePassword = config2.getField("oraclePassword");
                System.out.println("model.oracleUrl -> " + model.oracleUrl);
                System.out.println("model.oracleUser -> " + model.oracleUser);
                System.out.println("model.oraclePassword -> " + model.oraclePassword);
//        System.out.println(model.oracleUrl);
                logger.debug("Создаем обьект  Model() общение с Ora БД");

                /**
                 * ограничение количество строк получаемых в запросе
                 */
                model.limitRownum = config2.getField("limitRownum");
                logger.debug("определяем rownum максимальное колво записей в запросе {}", model.limitRownum);
//        logger.debug(" ", );
//
//                try {
//                    model.execSQLCNT(model.QUERY_MAILLOG_CNT, model.COLUMN_CNT);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }

                System.out.println("Не более чем " + model.limitRownum + " строк(и) ");
                /**
                 * Работа с базой раскоментировать после логгирования и рефактора!
                 * */

                /**
                 * Если записей больше нуля, то продолжаем
                 */

                /**
                 * Расскоментировать после проверки config.ini
                 */

//                logger.debug("model.execSQLQUERY_MAILLOG_DATA return {} ", model.execSQLQUERY_MAILLOG_DATA(model.QUERY_MAILLOG_DATA, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
//                        model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID));
                System.out.println("Метка");
//                if (model.execSQLQUERY_MAILLOG_DATA(model.QUERY_MAILLOG_DATA, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
//                        model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID)) {
//                    String s = model.response;
//                    System.out.println(s);
//                    logger.debug("Выполняем execSQLQUERY_MAILLOG_DATA метка {}", model.COLUMN_EMAIL);
//
//                    String[] responseAll = model.response.split(model.delimiterEnd);
//
//
//                    if (!model.response.equals("")) {
//
//                        for (int index = model.index; index < responseAll.length; index++) {
//
//                            System.out.print(" - " + responseAll[index]);
//                            response = responseAll[index].split(model.delimiter);
//                            String urlForPdf = response[4];
//                            String nameFile = response[5];
//                            String mail_id = response[5];
//                            GoToURL pdf = new GoToURL(urlForPdf, nameFile + dot + format, config2.getField("pathForTempFiles"));
//
//                            if (pdf.writeToFile) {
//                                System.out.println("Платежный документ создан!");
//                                logger.debug("Платежный документ создан!");
//
//                                if (model.execSQLUpdate(model.QUERY_UPDATE, state[1], mail_id)) {
//                                    logger.debug("в таблице maillog state изменен в 1 , отправляется!");
//                                    System.out.println("в таблице maillog state изменен в 1 , отправляется!");
//                                }
//                                EmailSender emailSender = new EmailSender(response, model.delimiter, format);
//                                System.out.println("config2.getField(pathForTempFiles) " + config2.getField("pathForTempFiles"));
//                                emailSender.setField("pathForTempFiles", config2.getField("pathForTempFiles"));
//
//                                System.out.println("emailSender.pathForTempFiles " + emailSender.pathForTempFiles);
//
//                                if (emailSender.setField("emailPassword", config2.getField("emailPassword"))
//                                        && emailSender.setField("fromMail", config2.getField("fromMail"))
//                                    // && emailSender.setField("mailProperties", config2.getField("mailProperties"))
//                                ) {
//                                    System.out.println("emailSender.passwordMail -> " + emailSender.emailPassword);
//                                    try {
//                                        if (emailSender.sendEmail()) {
//                                            logger.debug("Email отправлено! на {}", mail_id);
//                                            System.out.println("Email отправлено! на " + mail_id);
//
//                                            if (model.execSQLUpdate(model.QUERY_UPDATE, state[2], mail_id)) {
//                                                logger.debug("В таблице maillog state изменен в 2 , уже отправленное!");
//                                                System.out.println("В таблице maillog state изменен в 2 , уже отправленное!");
//                                            }
//                                        } else {
//                                            logger.debug("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
//                                            System.out.println("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
//                                            if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
//                                                logger.debug("В таблице maillog state изменен в -2 , письмо не отправлено!");
//                                                System.out.println("В таблице maillog state изменен в -2 , письмо не отправлено!");
//                                            }
//                                        }
//                                    } catch (IOException e) {
//                                        throw new RuntimeException(e);
//                                    } catch (MessagingException e) {
//                                        throw new RuntimeException(e);
//                                    }
//                                }
//
//                            } else {
//                                logger.debug("Ошибка создания файла ПД pdf");
//                                System.out.println("Ошибка создания файла ПД pdf");
//                                if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
//                                    logger.debug("В таблице maillog state изменен в -2 , не отправлено! ");
//                                    System.out.println("В таблице maillog state изменен в -2 , не отправлено! ");
//                                }
//                            }
//
//                        }
//                    }
//
//                }


//            };
//        }
    }

//    public ControllerSenderEmail() {
//
//    }

    /**
     *
     * @throws SQLException
     * @throws MessagingException
     * @throws IOException
     */
    public  void controllerSenderEmail() throws SQLException, MessagingException, IOException {
        taskControllerSenderEmail = () -> {

            Config2 config2 = new Config2();
            String format = config2.getField("formatFile");
            String dot = config2.getField("dot");

            /**
             //         * Создаем модель общение с БД
             //         */
            Model model = new Model();
            model.limitRownum = config2.getField("limitRownum");
            model.oracleUrl = config2.getField("oracleUrl");
            model.oracleUser = config2.getField("oracleUser");
            model.oraclePassword = config2.getField("oraclePassword");
            System.out.println("model.oracleUrl -> " + model.oracleUrl);
            System.out.println("model.oracleUser -> " + model.oracleUser);
            System.out.println("model.oraclePassword -> " + model.oraclePassword);
//        System.out.println(model.oracleUrl);
            logger.debug("Создаем обьект  Model() общение с Ora БД");

            /**
             * ограничение количество строк получаемых в запросе
             */
            model.limitRownum = config2.getField("limitRownum");
            logger.debug("определяем rownum максимальное колво записей в запросе {}", model.limitRownum);
//        logger.debug(" ", );

//            try {
//                model.execSQLCNT(model.QUERY_MAILLOG_CNT, model.COLUMN_CNT);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }

            System.out.println("Не более чем " + model.limitRownum + " строк(и) ");
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


//            if (model.execSQLQUERY_MAILLOG_DATA(model.QUERY_MAILLOG_DATA, model.COLUMN_EMAIL, model.COLUMN_STATE, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT,
//                    model.COLUMN_FILE_URL, model.COLUMN_MAIL_ID)) {
//                logger.debug("Выполняем execSQLQUERY_MAILLOG_DATA {}", model.COLUMN_EMAIL);
//
//                String[] responseAll = model.response.split(model.delimiterEnd);
//                ArrayList<String> responseRow = model.responseRow;
//
//
//
//
//                /**
//                 *
//
//                if (!model.response.equals("")) {
//
//                    for (int index = model.index; index < responseAll.length; index++) {
//
//                        System.out.print(" - " + responseAll[index]);
//                        response = responseAll[index].split(model.delimiter);
//                        String urlForPdf = response[4];
//                        String nameFile = response[5];
//                        String mail_id = response[5];
//                        GoToURL pdf = new GoToURL(urlForPdf, nameFile + dot + format, config2.getField("pathForTempFiles"));
//
//                        if (pdf.writeToFile) {
//                            System.out.println("Платежный документ создан!");
//                            logger.debug("Платежный документ создан!");
//
//                            if (model.execSQLUpdate(model.QUERY_UPDATE, state[1], mail_id)) {
//                                logger.debug("в таблице maillog state изменен в 1 , отправляется!");
//                                System.out.println("в таблице maillog state изменен в 1 , отправляется!");
//                            }
//                            EmailSender emailSender = new EmailSender(response, model.delimiter, format);
//                            System.out.println("config2.getField(pathForTempFiles) " + config2.getField("pathForTempFiles"));
//                            emailSender.setField("pathForTempFiles", config2.getField("pathForTempFiles"));
//
//                            System.out.println("emailSender.pathForTempFiles " + emailSender.pathForTempFiles);
//
//                            if (emailSender.setField("emailPassword", config2.getField("emailPassword"))
//                                    && emailSender.setField("fromMail", config2.getField("fromMail"))
//                                // && emailSender.setField("mailProperties", config2.getField("mailProperties"))
//                            ) {
//                                System.out.println("emailSender.passwordMail -> " + emailSender.emailPassword);
//                                try {
//                                    if (emailSender.sendEmail()) {
//                                        logger.debug("Email отправлено! на {}", mail_id);
//                                        System.out.println("Email отправлено! на " + mail_id);
//
//                                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[2], mail_id)) {
//                                            logger.debug("В таблице maillog state изменен в 2 , уже отправленное!");
//                                            System.out.println("В таблице maillog state изменен в 2 , уже отправленное!");
//                                        }
//                                    } else {
//                                        logger.debug("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
//                                        System.out.println("Ошибка во время отправки emailSender.sendEmail() письмо не отправлено!");
//                                        if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
//                                            logger.debug("В таблице maillog state изменен в -2 , письмо не отправлено!");
//                                            System.out.println("В таблице maillog state изменен в -2 , письмо не отправлено!");
//                                        }
//                                    }
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                } catch (MessagingException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//
//                        } else {
//                            logger.debug("Ошибка создания файла ПД pdf");
//                            System.out.println("Ошибка создания файла ПД pdf");
//                            if (model.execSQLUpdate(model.QUERY_UPDATE, state[3], mail_id)) {
//                                logger.debug("В таблице maillog state изменен в -2 , не отправлено! ");
//                                System.out.println("В таблице maillog state изменен в -2 , не отправлено! ");
//                            }
//                        }
//
//                    }
//                }
//                */
//            }



        };
    }
}
