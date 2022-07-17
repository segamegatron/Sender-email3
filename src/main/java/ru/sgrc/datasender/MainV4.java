package ru.sgrc.datasender;

import ru.sgrc.datasender.email.EmailSender;

public class MainV4 {
    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender();
        emailSender.setField("emailPassword", "");
        emailSender.setField("fromMail", "");
        emailSender.setField("mailProperties", "C:\\Config\\");
        emailSender.setField("pathForTempFiles", "");
    }
}
