package bot;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class ThreadReadFromSocket extends Thread {

    private Socket socket;
    private CopyOnWriteArrayList<ByteBuffer> messagesReceived;

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    private String threadName = "";

    public boolean isThreadActive() {
        return this.threadActive;
    }

    public void setThreadActive(boolean threadActive) {
        this.threadActive = threadActive;
    }

    private boolean threadActive;

    public ThreadReadFromSocket(Socket socket, CopyOnWriteArrayList<ByteBuffer> messagesReceived) {
        this.socket = socket;
        this.messagesReceived = messagesReceived;
        this.threadActive = true;
    }

    @Override
    public void run() {
        //Logger.getInstance().log("|"+ this.threadName +"| ThreadReadFromSocket started");

        try {
            while (this.threadActive) {

                BufferedInputStream bufferedInputStream = new BufferedInputStream(this.socket.getInputStream());
                DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

                short messageLength = dataInputStream.readShort();

                Logger.getInstance().log("|" + this.threadName + "| expecting message of length -> " + messageLength);

                System.out.println("|" + this.threadName + "| expecting message of length -> " + messageLength);

                if (messageLength != 0) {

                    ByteBuffer allMessageBytes = ByteBuffer.allocate(messageLength);
                    boolean end = false;

                    boolean justReceived = true;

                    int totalBytesRead = 0;
                    while (!end) {

                        byte[] messagePartHelp = new byte[messageLength];

                        int currentBytesReadCount = dataInputStream.read(messagePartHelp);
                        System.out.println("currentBytesReadCount " + currentBytesReadCount);

                        byte[] messagePart = new byte[currentBytesReadCount];

                        System.arraycopy(messagePartHelp, 0, messagePart, 0, currentBytesReadCount);


                        if (currentBytesReadCount == 0) {
                            break;
                        }

                        //quick ar
                        if (currentBytesReadCount > 10000) {
                            System.out.println("skipping message larger than 10k bytes");
                            break;
                        }

                        if (justReceived) {
                            justReceived = false;
                            byte byte1 = messagePart[0];
                            byte byte2 = messagePart[1];

                            ByteBuffer bb = ByteBuffer.allocate(2);
                            bb.order(ByteOrder.BIG_ENDIAN);
                            bb.put(byte1);
                            bb.put(byte2);
                            short shortVal = bb.getShort(0);

                            //System.out.println("message type is -> " + shortVal);
                        }

                        totalBytesRead = currentBytesReadCount + totalBytesRead;
                        Logger.getInstance().log("|" + this.threadName + "| read bytes -> " + totalBytesRead);

                       // System.out.println("|" + this.threadName + "| read bytes -> " + totalBytesRead);


                        if (totalBytesRead <= messageLength) {
                            System.out.println("totalBytesRead <= length");
                            System.out.println("allMessageBytes.put(messagePart) lengh " + messagePart.length);
                            System.out.println("number of bytes present in bytebuffer " + allMessageBytes.position() + " / " + messageLength);

                            allMessageBytes.put(messagePart);
                            System.out.println("number of bytes present in bytebuffer " + allMessageBytes.position() + " / " + messageLength);


                        }

                        if (totalBytesRead >= messageLength) {
                            end = true;
                        }
                    }

                    Logger.getInstance().log("|" + this.threadName + "| received message of length " + messageLength);

                    System.out.println("|" + this.threadName + "| received message of length " + messageLength);


                    this.messagesReceived.add(allMessageBytes);
                }
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            Logger.getInstance().log("|" + this.threadName + "| exception caught " + exceptionAsString);


            System.out.println("|" + this.threadName + "| exception caught " + exceptionAsString);
        }
    }
}
