package bot;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.TextArea;

import java.util.Objects;


public class WindowApplicationLog {

    private Scene scene;
    private Pane root;
    private Stage stage;
    private TextArea textArea1;
    private boolean isVisible;

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        if (this.isVisible) {
            this.stage.show();
        } else {
            this.stage.hide();
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void initWindow() {
        try {
            this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("WindowApplicationLog.fxml")));
            this.isVisible = true;
        } catch (Exception e) {
            System.out.println("exception -> " + e.getMessage());
        }

        this.stage = new Stage();
        this.stage.setTitle("bot log");
        this.scene = new Scene(this.root, 300, 275);
        this.scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("main.css")).toExternalForm());
        this.stage.setScene(this.scene);
        this.stage.setResizable(true);
        this.stage.show();

        this.textArea1 = (TextArea)this.scene.lookup("#textarea1");

        WindowApplicationLog.WindowOnCloseHandler windowOnCloseHandler = new WindowApplicationLog.WindowOnCloseHandler();
        this.stage.setOnCloseRequest(windowOnCloseHandler);

        Logger.getInstance().setTextAreaLog(this.textArea1);

    }

    private class WindowOnCloseHandler implements EventHandler<WindowEvent> {

        @Override
        public void handle(WindowEvent t) {

        }
    }
}
