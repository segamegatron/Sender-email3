package ru.sgrc.datasender;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class Model {


    public final String delimiter = ";;;";
    public final String delimiterEnd = "!!!";
    public int index = 0;

    public String QUERY_MAILLOG_MAILACCOUNT =
            "select m.* ,ma.email ,ma.sender ,ma.login ,ma.passw ,ma.usessl ,ma.serveraddr ,ma.serverport FROM kmh.t_maillog m JOIN kmh.t_mail_account ma ON ma.acc_id = m.acc_id WHERE state in (?, ?) and rownum < ?";
    public String QUERY_MAIL_ACCOUNT =
            "select * from kmh.t_mail_account ma WHERE ma.acc_id = ?";

    protected  String QUERY_TEST =
            "SELECT n_lic l ,surname s FROM kmh.t_lics where f_negil = ? and rownum < ?";

    public final String QUERY_MAILLOG_CNT =
            "SELECT count(*) CNT  FROM kmh.t_maillog where state in (?, ?)";

    public final String QUERY_MAILLOG_DATA =
            "SELECT * FROM kmh.t_maillog WHERE state in (?, ?) and rownum < ?";

    public final String QUERY_UPDATE =
            "UPDATE kmh.t_maillog m SET state = ? WHERE m.mail_id = ? ";

    protected final String QUERY_FOR_DELETE_FILE =
//            "SELECT m.bdate ,m.fdate FROM kmh.t_maillog WHERE state = 2 and rownum < ? ";
          "SELECT m.mail_id ,m.bdate ,m.fdate FROM kmh.t_maillog m WHERE state = 2 and m.fdate > kmh.cur_month ";

    // model.COLUMN_EMAIL, model.COLUMN_MAILHEAD, model.COLUMN_MAILTEXT, model.COLUMN_FILE_URL, model.COLUMN_STATE)
    protected final String COLUMN_N_LIC = "l";
    protected final String COLUMN_SURNAME = "s";
    public final String COLUMN_CNT = "CNT";

    protected final String COLUMN_BDATE = "BDATE";
    protected final String COLUMN_FDATE = "FDATE";
    public String COLUMN_EMAIL = "EMAIL";
    public final String COLUMN_MAILHEAD = "MAILHEAD";
    public final String COLUMN_MAILTEXT = "MAILTEXT";
    public final String COLUMN_FILE_URL = "FILE_URL";
    public final String COLUMN_STATE = "STATE";
    public final String COLUMN_MAIL_ID = "MAIL_ID";
    protected  int f_negil = 0;
    protected  int cstate = 0;

    public  String COLUMN_ACC_ID = "ACC_ID";
    public String COLUMN_EMAIL_SENDER = "EMAIL";
    public String COLUMN_SENDER = "SENDER";
    public String COLUMN_LOGIN = "LOGIN";
    public String COLUMN_PASSW = "PASSW";
    public String COLUMN_USESSL = "USESSL";
    public String COLUMN_SERVER = "SERVERADDR";
    public String COLUMN_SERVERPORT = "SERVERPORT";


    public String oracleUrl;
    public String oracleUser;
    public String oraclePassword;
    public   String limitRownum ;

    private int cstate2 = 1;

    /* todo авто заполнение responseRow*/
    public ArrayList<String[]> responseRow;
    public ArrayList<String[]> responseRow2=null;

    Logger logger = LoggerFactory.getLogger(Model.class);


    public Model() {
//        this.URL = url;
//        this.USER = user;
//        this.PASSWORD = password;
        //this.DataFromUrl = DataFromUrl;
//        if (Thread.currentThread().getName().equals("SenderEmail")) {
//
            Main.secondsTestControllerSenderEmail = Main.getSecondsTimeNow();
//        }
        logger.debug("Выполнен конструктор Model - создание обьекта для связи с ораклом");
    }

    public boolean setField(String field) {

        return false;
    }

    public boolean decoratorConnection(String query, int[] preparedStatements, String[] columns) {
        try (Connection connection =
                     DriverManager.getConnection(oracleUrl, oracleUser, oraclePassword);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            int element=1;
            for (int prepareSt:preparedStatements) {

                preparedStatement.setInt(element++,  prepareSt);
            }
//            preparedStatement.setInt(2,  cstate2);
            ResultSet resultSet = preparedStatement.executeQuery();
            //System.out.println(resultSet.next());
//            StringBuilder response = new StringBuilder("");
//            ArrayList<String> responseRow = new ArrayList<>();
            ArrayList<String[]> responseRow = new ArrayList<>();
            ArrayList<String[]> responseRow2 = new ArrayList<>();
//            String[] columns = { this.COLUMN_EMAIL, this.COLUMN_STATE, this.COLUMN_MAILHEAD, this.COLUMN_MAILTEXT, this.COLUMN_FILE_URL, this.COLUMN_MAIL_ID};


            while (resultSet.next()) {
                String[] strCols = new String[columns.length];
                for (String column : columns) {
                    strCols[index++] = resultSet.getString(column);

                }
                responseRow.add(strCols);


                String accId = resultSet.getString(this.COLUMN_ACC_ID);
                String emailSender = resultSet.getString(this.COLUMN_EMAIL_SENDER);
                String sender = resultSet.getString(this.COLUMN_SENDER);
                String login = resultSet.getString(this.COLUMN_LOGIN);
                String passw = resultSet.getString(this.COLUMN_PASSW);
                String usessl = resultSet.getString(this.COLUMN_USESSL);
                String server = resultSet.getString(this.COLUMN_SERVER);
                String serverport = resultSet.getString(this.COLUMN_SERVERPORT);
                logger.debug("Формируем Обьект данных {} ", emailSender);

                String[] row = { accId, emailSender, sender, login, passw, usessl, server, serverport};
                responseRow2.add(row);

            }
            preparedStatement.close();
            connection.close();
//            Main.connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable = connection.isClosed();
            logger.debug("decoratorConnection() connection.isClosed() {}", connection.isClosed());
            this.responseRow = responseRow;
            this.responseRow2 = responseRow2;

            return true;
        } catch (SQLException e) {
            /**
             * Ошибка формирование строки запроса либо Oracle вернул ошибку
             */
            System.out.println("e.getMessage()");
            System.out.println(e.getMessage());
//            logger.debug("connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable {} ", connection.isClosed());

//            connectionOracle("").close();
            return false;
//        } finally {
//            Main.connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable = connection.isClosed();

        }
    }

    public boolean execSQLQUERY_MAILLOG_DATA_Closeable(String query) {
        try (Connection connection =
                      DriverManager.getConnection(oracleUrl, oracleUser, oraclePassword);
              PreparedStatement preparedStatement =
                      connection.prepareStatement(query)) {
            Main.connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable = connection.isClosed();
            connection.getNetworkTimeout();
//            logger.debug("connection.getNetworkTimeout() {} ", connection.getNetworkTimeout());
//            logger.debug("Thread.currentThread().getId() {} ", Thread.currentThread().getId());
//            Thread.currentThread().stop();
//            Executors.
//            connection.setNetworkTimeout(Executors.newFixedThreadPool(1), yourTimeout);
//            connection.setNetworkTimeout(Executors.newFixedThreadPool((int)Thread.currentThread().getId()), 5000);
//            Thread.sleep(600000);
//            logger.debug("connection.isClosed() {} ",connection.isClosed());
//            logger.debug("connection.getNetworkTimeout() {} ", connection.getNetworkTimeout());

//            logger.debug("connection.isClosed() {}", connection.isClosed());
            preparedStatement.setInt(1,  cstate);
            preparedStatement.setInt(2,  cstate); // cstate2);
            preparedStatement.setInt(3, Integer.parseInt(limitRownum));
//            preparedStatement.setString(2, "P%");

            ResultSet resultSet = preparedStatement.executeQuery();
            //System.out.println(resultSet.next());
//            StringBuilder response = new StringBuilder("");
//            ArrayList<String> responseRow = new ArrayList<>();
            ArrayList<String> responseRow = new ArrayList<>();
            ArrayList<String[]> responseRow2 = new ArrayList<>();
            String[] columns = { this.COLUMN_EMAIL, this.COLUMN_STATE, this.COLUMN_MAILHEAD, this.COLUMN_MAILTEXT, this.COLUMN_FILE_URL, this.COLUMN_MAIL_ID};


            while (resultSet.next()) {

                String email1 = resultSet.getString(this.COLUMN_EMAIL);
                String state1 = resultSet.getString(this.COLUMN_STATE);
                String mailHead1 = resultSet.getString(this.COLUMN_MAILHEAD);
                String mailText1 = resultSet.getString(this.COLUMN_MAILTEXT);
                String fileUrl1 = resultSet.getString(this.COLUMN_FILE_URL);
                String mailId1 = resultSet.getString(this.COLUMN_MAIL_ID);
                String accId = resultSet.getString(this.COLUMN_ACC_ID);
                String emailSender = resultSet.getString(this.COLUMN_EMAIL_SENDER);
                String sender = resultSet.getString(this.COLUMN_SENDER);
                String login = resultSet.getString(this.COLUMN_LOGIN);
                String passw = resultSet.getString(this.COLUMN_PASSW);
                String usessl = resultSet.getString(this.COLUMN_USESSL);
                String server = resultSet.getString(this.COLUMN_SERVER);
                String serverport = resultSet.getString(this.COLUMN_SERVERPORT);
                logger.debug("Формируем Обьект данных {} ", email1);

                String[] row = { email1, state1, mailHead1, mailText1, fileUrl1, mailId1,
                        accId, emailSender, sender, login, passw, usessl, server, serverport };
//                for (String s:row) {

//                    logger.debug("Model Oracle {} ", s);
//                }
                responseRow2.add(row);

            }
            preparedStatement.close();
            connection.close();
            Main.connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable = connection.isClosed();
//            logger.debug("connection.isClosed() {}", connection.isClosed());
            connection.isClosed();
//            this.responseRow = responseRow;
            this.responseRow2 = responseRow2;
            return true;
//            System.out.println("from Model -> " + this.response);
        } catch (SQLException e) {
            /**
             * Ошибка формирование строки запроса либо Oracle вернул ошибку
             */

//            logger.debug("connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable {} ", connection.isClosed());

//            connectionOracle("").close();
            return false;
//        } finally {
//            Main.connectionStateExecSQLQUERY_MAILLOG_DATA_Closeable = connection.isClosed();
        }
//        connectionOracle("").close();
        /**
         * Запрос отработан успешно, поле response обновлено
         */

    }
    public  boolean execSQLUpdate(String query, int state, String mail_id) {
        try (Connection connection =
                     DriverManager.getConnection(oracleUrl, oracleUser, oraclePassword);
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {
            Main.connectionStateExecUpdate = connection.isClosed();
            preparedStatement.setInt(1, state);
            preparedStatement.setInt(2, Integer.parseInt(mail_id));
//            preparedStatement.setString(2, "P%");
            ResultSet resultSet = preparedStatement.executeQuery();

//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                this.result = (String) resultSet.getString(columnLable);
//                System.out.println(resultSet.getString(columnLable));
//            }
            connection.close();
            Main.connectionStateExecUpdate = connection.isClosed();
            return true;
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return false;

    }



/*
    public boolean execSQLQUERY_FOR_DELETE_FILE(String query, String n_lic, String surname) {
        try ( PreparedStatement preparedStatement = connectionOracle(query) ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            StringBuilder response = new StringBuilder("");
            while (resultSet.next()) {
                response.append(resultSet.getString(this.COLUMN_BDATE))
                        .append(delimiter);
            }
            this.response = response.toString();
            System.out.println("from Model -> " + this.response);
        } catch (SQLException e) {

            System.out.println("e.getMessage()");
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
*/

}
