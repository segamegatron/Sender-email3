package ru.sgrc.datasender;

import static java.lang.System.*;

public class Test9 implements CurrentRunThread{
    @Override
    public int getSecondsTimeNow() {
        return 0;
    }


    public static void main(String[] args) {
        CurrentRunThread currentRunThread = ()->  10;
        out.println(currentRunThread.getSecondsTimeNow());
    }

}
