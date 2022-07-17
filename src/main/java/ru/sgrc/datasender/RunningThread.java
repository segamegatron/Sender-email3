package ru.sgrc.datasender;

import ru.sgrc.datasender.email.TestControllerSenderEmail;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class RunningThread {
//      SenderThread thread;
      Thread thread;
    int value;

    public RunningThread(){
//        this.thread = null;
    }

    public Date getUpdate(){
        if (this.thread == null)
            return null;
//        return this.thread.dUpdate;
        return new Date();
    }

    protected void setThread (int value) throws SQLException, MessagingException, IOException {
        this.value = value;
        switch (value){
            case 0:
//                this.thread = new TestControllerSenderEmail();
                break;
            case 1:
//                this.thread = new ControllerClearDir();
                break;
//            case 0:
//                this.thread = new ControllerClearDir();
//                break;
        }
    }
}


