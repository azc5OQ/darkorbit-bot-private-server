package bot;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadWriteToSocket extends Thread {

    private Socket socket;
    private CopyOnWriteArrayList<ByteBuffer> messagesToSend;

    public boolean isThreadActive() {
        return this.threadActive;
    }

    public void setThreadActive(boolean threadActive) {
        this.threadActive = threadActive;
    }

    private boolean threadActive;


    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    private String threadName = "";


    public ThreadWriteToSocket(Socket socket, CopyOnWriteArrayList<ByteBuffer> messagesToSend) {
        this.socket = socket;
        this.messagesToSend = messagesToSend;
        this.threadActive = true;
    }

    @Override
    public void run() {

        Logger.getInstance().log("|" + this.threadName + "| messagesToSend");

        try {
            while (this.threadActive) {

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getInstance().log(ex.getMessage());
                }

                //  alternative for (int i = this.messagesToSend.size() - 1; i >= 0; i--) {
                this.messagesToSend.forEach(singleMessage -> {

                    Logger.getInstance().log("|" + this.threadName + "| sending message -> " + singleMessage.array().length);

                    try {
                        this.socket.getOutputStream().write(singleMessage.array());
                    } catch (IOException ex) {
                        StringWriter sw = new StringWriter();
                        ex.printStackTrace(new PrintWriter(sw));
                        String exceptionAsString = sw.toString();

                        Logger.getInstance().log("|" + this.threadName + "| exception caught: " + exceptionAsString);
                    }

                    //Logger.getInstance().log("|" + this.threadName + "| message sent");

                    this.messagesToSend.remove(singleMessage);
                });
            }
        } catch (Exception ex) {

            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            Logger.getInstance().log("|" + this.threadName + "| exception caught: " + exceptionAsString);
        }
    }
}