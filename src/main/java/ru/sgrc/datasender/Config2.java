package ru.sgrc.datasender;


import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Paths.get;

public class Config2 {
    private String delay;
    private String oracleUrl;
    private String formatFile = "-1";
    private String pathForTempFiles;
    private String pathMailProperties;
    private String fromMail;
    private String emailPassword;

    private String oracleUser;
    private String dot;
    private String oraclePassword;
    private String limitRownum;
    private String delimiter;

    //    String path = "C:\\PDF\\";
    String fileName = "C:\\Config\\config.ini";
    List<String> list;

    public Config2() {

        try (BufferedReader br = Files.newBufferedReader(get(fileName))) {
            list = br.lines().collect(Collectors.toList());

        } catch (
                IOException e) {
            e.printStackTrace();
        }
        /*todo
        *  Переформировать в case либо обьект enum*/
        for (int step = 0; step < 13; step++) {
            if (list.get(step).split("=")[0].equals("DELAY")) {
                delay = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("PATH_FOR_TEMP_FILES")) {
                pathForTempFiles = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("FORMATFILE")) {
                formatFile = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("DELIMITER")) {
                delimiter = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("DOT")) {
                dot = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("PATH_MAIL_PROPERTIES")) {
                pathMailProperties = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("FROMMAIL")) {
                fromMail = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("PASSWORDMAIL")) {
                emailPassword = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("ORACLE_URL")) {
                oracleUrl = list.get(step).split("=")[1];
                System.out.println("config2.ini oracleUrl -> " + oracleUrl);
                continue;
            }
            if (list.get(step).split("=")[0].equals("ORACLE_USER")) {
                oracleUser = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("ORACLE_PASSWORD")) {
                oraclePassword = list.get(step).split("=")[1];
                continue;
            }
            if (list.get(step).split("=")[0].equals("LIMIT_ROWNUM")) {
                limitRownum = list.get(step).split("=")[1];
            }
//                System.out.println(step);
//            }

//        if (list.get(0).split("=")[0].equals("FORMATFILE")) {
//            pathDir = list.get(0).split("=")[1];
//        }


//        String[] configs = {"PATH_FOR_TEMP_FILES"};
//        for (String element : configs) {

//        }
//        String[] key1 = list.get(0).split("=");
//        String vkey1 = key1[0];
//        String vvalue1 = key1[1];

//        System.out.println(vkey1 +" = "+ vvalue1);
        }
    }

    public String getField(@NotNull String field) {
        if (field.equals("pathMailProperties")) {
            return this.pathMailProperties;
        }
        if (field.equals("fromMail")) {
            return this.fromMail;
        }
        if (field.equals("emailPassword")) {
            return this.emailPassword;
        }
        if (field.equals("oracleUrl")) {
            return this.oracleUrl;
        }
        if (field.equals("oracleUser")) {
            return this.oracleUser;
        }
        if (field.equals("dot")) {
            return this.dot;
        }
        if (field.equals("oraclePassword")) {
            return this.oraclePassword;
        }
        if (field.equals("limitRownum")) {
            return this.limitRownum;
        }
        if (field.equals("delimiter")) {
            return this.delimiter;
        }
        if (field.equals("formatFile")) {
            return this.formatFile;
        }
        if (field.equals("pathForTempFiles")) {
            return this.pathForTempFiles;
        }
        if (field.equals("delay")) {
            return this.delay;
        }


        return new String("");
    }
}

