package ru.sgrc.datasender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sgrc.datasender.email.TestControllerSenderEmail;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RunnableControllerSenderEmail extends ControllerSenderEmail implements Runnable, CurrentRunThread{
    private final Config2 config2;
    volatile Date  dateRunnableControllerSenderEmail;
    Logger logger = LoggerFactory.getLogger(Model.class);
    /**
     * @throws SQLException
     * @throws MessagingException
     * @throws IOException
     */
    RunnableControllerSenderEmail(Config2 config2) throws SQLException, MessagingException, IOException {
        this.config2 = config2;
    }

    public void execControllerSenderEmail(Config2 config2) throws SQLException, MessagingException, IOException, InterruptedException {
        Main.secondsTestControllerSenderEmail = Main.getSecondsTimeNow();

        /* Отладка потока SenderEmail */
//        logger.debug("secondsTestControllerSenderEmail {}", Main.secondsTestControllerSenderEmail);
//        Thread.sleep(90000);
//        Thread.currentThread().stop();


        TestControllerSenderEmail testControllerSenderEmail = new TestControllerSenderEmail(config2);
        testControllerSenderEmail.controllerSenderEmail();
        dateRunnableControllerSenderEmail = testControllerSenderEmail.dateTestControllerSenderEmail;
        Main.secondsTestControllerSenderEmail = Main.getSecondsTimeNow();
    }

    @Override
    public void run() {
        try {
            execControllerSenderEmail(config2);
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
