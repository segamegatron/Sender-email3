package ru.sgrc.datasender.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import ru.sgrc.datasender.Controller;
import ru.sgrc.datasender.ControllerClearDir;

import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;


/**
 * Основная задача отправить почту, без обработок данных,
 * в конструктор получили адрес, тему, текст, путь к файлу для вложения письма и отправили.
 * ошибка при отправке в логе, произошла ли доставка не проверяем как вариант собщение дублируем на контрольный адрес
 * для косвенного подтверждения отправки.
 */

public class EmailSender {
    private  String host;
    private  String port;

//    final String  port = "mail.smtps.port=465";


    String fromMail = "noreply@yandex.ru";
    String toMail = "t8883312@gmail.com";
    String subject = " Платежный документ";
    String text = "Платежный документ во вложении";
    String loginMail = fromMail;
    public String emailPassword;
    String mailProperties = "mail.properties";
    String file = "8.pdf";
    String format = "pdf";
    String file_url = "http";
    String[] dataForSend, d;
    private boolean sent = true;
    Logger logger = LoggerFactory.getLogger(EmailSender.class);
    public String pathForTempFiles = "C:\\F\\";
    String mail_smtp_auth =  "true";
    public EmailSender(String[] response, String format) {

        logger.debug("Коснструктор EmailSender ");
        this.format = format;
        System.out.println(Arrays.toString(response));
        this.toMail = response[0];
        System.out.println(toMail);
        System.out.println(this.subject = response[2]);
        System.out.println(this.text = response[3]);
        System.out.println(this.file_url = response[4]);
        System.out.println(this.file = response[5] + ControllerClearDir.dot + ControllerClearDir.format);

        String[] vars = {mail_smtp_auth };
    }

    public EmailSender() {

    }

    public EmailSender(String toEmail, String subject, String text, String file, String fromEmail, String host, String port, String format, String pathForTempFiles, String emailPassword) {
        this.toMail = toEmail;
        this.subject = subject;
        this.text = text;
        this.file = file + "." + format;
        this.fromMail = fromEmail;
        this.host = host;
        this.port = port;
        this.format = format;
        this.pathForTempFiles = pathForTempFiles;
        this.emailPassword = emailPassword;
    }

    public boolean sendEmail() throws IOException, MessagingException, InterruptedException {
        Properties properties = new Properties();
        /** Получение настроек из файла
        properties.load(EmailSender.class.getClassLoader().getResourceAsStream("mail.properties"));
        logger.debug("properties.isEmpty() {}", properties.isEmpty());
         */

        /** Настройки в ручную */
        setProertyies(host, port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");





        /**
         * Создание сессии с вложеным properties
         */
        Session mailSession = Session.getDefaultInstance(properties);

        /**
         * Формирование MIME типов с вложенной сессией
         */
        MimeMessage message = new MimeMessage(mailSession);

        /**
         * Отправитель
         */
        message.setFrom(new InternetAddress(fromMail));

        /**
         * Получатель
         */
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));

        /**
         * Тема письма
         */
        message.setSubject(subject);

        /**
         * Часть текстового контента
         */
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);

        /**
         * Часть прикрепленного контента  (файл вложения)
         */
        logger.debug("attachmentPart.attachFile(new File(pathForTempFiles + file)) {} ", pathForTempFiles+file);
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(new File(pathForTempFiles + file));

        /**
         * Обьединение частей контента
         */
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);

        /**
         * Подсоединить MIME тип контенту письма
         */
        message.setContent(multipart);

        /**
         * Упаковка и отправка email
         */
//        emailPassword = "12345678";
//        toMail="it@sgrc.ru";
        logger.debug("Попытка отправки письма ------------------------");
        logger.debug("from mail  ------------------------ {} ", fromMail);
        logger.debug("To mail  ------------------------ {} ", toMail);
        logger.debug("from mail  ------------------------ {} ", emailPassword);
        Transport transport = mailSession.getTransport();
        try {
            transport = mailSession.getTransport();
            transport.connect(fromMail, emailPassword);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            System.out.println("Перехват -> " + e.getMessage());
            this.sent = false;
        } finally {
            transport.close();
        }
        if (this.sent) {
//            System.out.println("In if -> "+this.sent);
            logger.debug("Письмо отправлено на {} с вложенным файлом {}", toMail, file);
            return true;
        }
//        System.out.println("Out if -> " + this.sent);
        return false;
    }

    private void setProertyies(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public boolean setField(String field, String value) {
        if (field.equals("emailPassword")) {
            emailPassword = value;
            return true;
        }
        if (field.equals("fromMail")) {
            fromMail = value;
            return true;
        }
        if (field.equals("mailProperties")) {
            mailProperties = value;
            return true;
        }
        if (field.equals("pathForTempFiles")) {
            pathForTempFiles = value;
            return true;
        }

        return false;
    }

    /** Тест работает!
    public static void main(String[] args) throws MessagingException, IOException, InterruptedException {
        EmailSender emailSender = new EmailSender();
        emailSender.logger.debug("emailSender.pathForTempFiles {}", emailSender.pathForTempFiles);
        emailSender.logger.debug("emailSender.pathForTempFiles {}", emailSender.mailProperties);
        if (emailSender.sendEmail()) {
            emailSender.logger.debug("вроде отправлено, проверь!");
        }
    }
     */
}