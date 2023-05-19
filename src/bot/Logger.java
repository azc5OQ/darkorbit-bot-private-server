//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bot;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private TextArea textAreaLog;

    public Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public void setTextAreaLog(TextArea textAreaLog) {
        this.textAreaLog = textAreaLog;
    }

    public void log(String valueToLog) {

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = dateTimeFormatter.format(localDateTime);

        valueToLog = formattedDate + " | " + valueToLog;

        if (this.textAreaLog != null) {
            SingleLogAction singleLogAction = new SingleLogAction(valueToLog, this.textAreaLog);
            Platform.runLater(singleLogAction); //similar to winforms C# Invoke, for updating the GUI from another thread
        }
    }

    private class SingleLogAction implements Runnable {

        private String valueToLog;
        private TextArea textAreaLog;

        SingleLogAction(String valueToLog, TextArea textAreaLog) {
            this.valueToLog = valueToLog;
            this.textAreaLog = textAreaLog;
        }

        @Override
        public void run() {
            this.textAreaLog.setText(this.textAreaLog.getText() + valueToLog + "\n");
        }
    }
}
