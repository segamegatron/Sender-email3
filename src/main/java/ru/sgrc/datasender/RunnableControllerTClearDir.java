package ru.sgrc.datasender;

import ru.sgrc.datasender.cls.ControllerTClearDir;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class RunnableControllerTClearDir  implements Runnable, CurrentRunThread{
    private final String[] config;
    Date dateRunnableControllerClearDir;

    /**
     * @throws SQLException
     * @throws MessagingException
     * @throws IOException
     */
    RunnableControllerTClearDir(String[] config) throws SQLException, MessagingException, IOException {
        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
        this.config = config;
    }

    public void execControllerTClearDir(String[] config) throws SQLException, MessagingException, IOException, InterruptedException {
        Main.secondsControllerClearDir = Main.getSecondsTimeNow();
        new ControllerTClearDir(config[0], Integer.parseInt(config[1]));

    }

    @Override
    public void run() {
        try {
            execControllerTClearDir(config);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSecondsTimeNow() {
        return Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()))*60
                +Integer.parseInt(new SimpleDateFormat("ss").format(Calendar.getInstance().getTime()));
    }
}
