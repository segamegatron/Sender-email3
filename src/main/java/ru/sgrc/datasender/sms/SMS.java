package ru.sgrc.datasender.sms;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import static java.lang.System.*;

public class SMS {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, URISyntaxException, ParseException {
        //Инициализация класаа SmsSender
        SmsSender sms = new SmsSender("smsword","ab49834bb4501",false);


        //Отправка смс сообщения
        JSONObject resultJson = sms.MessageSend("тест", "79832336644", "mainsms");
        if (resultJson.get("status").equals("success"))
        {
            out.println("Сообщение успешно отправленно, стоимость отправки: "+resultJson.get("price")+" рублей");
        }
        else
        {
            out.println("Произошла ошибка: "+resultJson.get("message"));
        }


        // Запрос статуса
        resultJson = sms.MessageStatus("230022990,230022991");
        if (resultJson.get("status").equals("success"))
        {
            out.println("Статус сообщений: "+resultJson.get("messages"));
        }
        else
        {
            out.println("Произошла ошибка: "+resultJson.get("message"));
        }


        //Запрос цены
        resultJson = sms.MessagePrice("тест", "79832336644", "mainsms");
        if (resultJson.get("status").equals("success"))
        {
            out.println("Стоимость сообщений: "+resultJson.get("price") + " рублей");
        }
        else
        {
            out.println("Произошла ошибка: "+resultJson.get("message"));
        }



        //Запрос баланса
        resultJson = sms.MessageBalance();
        if (resultJson.get("status").equals("success"))
        {
            out.println("Баланс: "+resultJson.get("balance") + " рублей");
        }
        else
        {
            out.println("Произошла ошибка: "+resultJson.get("message"));
        }

    }
}
