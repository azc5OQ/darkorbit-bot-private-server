package bot;

import bot.object.Agent;
import bot.object.AgentType;
import bot.object.Box;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Objects;


public class BotWindow extends Application {

    public Pane root;
    private Scene scene;

    private Button startButton;

    private ThreadBotLogic botLogic;

    private Canvas minimapCanvas;

    private MenuBar menubar1;

    private TextField textFieldHost;
    private TextField textFieldPort;
    private TextField textFieldSessionId;
    private TextField textFieldUserId;

    private TextField textFieldFactionId;

    private MenuItem menuItemShowLog;

    private WindowApplicationLog windowApplicationLog;


    private BotClient botClient;
    private BotWindow.CanvasAnimationTimer CanvasAnimationTimer;

    public WindowApplicationLog getWindowApplicationLog() {
        return this.windowApplicationLog;
    }

    public void setWindowApplicationLog(WindowApplicationLog windowApplicationLog) {
        this.windowApplicationLog = windowApplicationLog;
    }

    public void init(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            this.root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("BotWindow.fxml")));
        } catch (IOException e) {
            System.out.println("exception -> " + e.getMessage());
        }

        if (this.root == null) {
            return;
        }

        this.scene = new Scene(this.root, 500, 375);
        primaryStage.setScene(this.scene);

        this.scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("main.css")).toExternalForm());


        primaryStage.setScene(this.scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(668);
        primaryStage.setHeight(708);
        primaryStage.show();

        this.startButton = (Button)this.scene.lookup("#startButton");
        this.startButton.setLayoutX(this.scene.getWidth() / 2);
        this.startButton.setLayoutY(this.scene.getHeight() / 2);


        StartBotButtonOnClick startBotButtonOnClick = new StartBotButtonOnClick(this);

        this.startButton.setOnAction(startBotButtonOnClick);

        this.root.getChildren().addAll(this.startButton);
        WindowOnCloseHandler windowOnCloseHandler = new WindowOnCloseHandler();
        primaryStage.setOnCloseRequest(windowOnCloseHandler);

        this.menubar1 = (MenuBar)this.scene.lookup("#menubar1");

        if (this.menubar1 == null)
        {
            System.out.println("menubar is null");
        }

        //pridaj druhy menu item
        this.menuItemShowLog = new MenuItem("show log window");
        System.out.println("this.menuItemShowLog = new MenuItem(\"show log window\")");
        this.menubar1.getMenus().get(0).getItems().add(this.menuItemShowLog);
        System.out.println("this.menubar1.getMenus().get(0).getItems().add(this.menuItemShowLog)");
        MenuItemShowLogOnClick menuItemShowLogOnClick = new MenuItemShowLogOnClick(this);
        System.out.println("MenuItemShowLogOnClick menuItemShowLogOnClick = new MenuItemShowLogOnClick(this)");
        this.menuItemShowLog.setOnAction(menuItemShowLogOnClick);

        this.textFieldHost = (TextField)this.scene.lookup("#textFieldHost");
        this.textFieldPort = (TextField)this.scene.lookup("#textFieldPort");
        this.textFieldSessionId = (TextField)this.scene.lookup("#textFieldSessionId");
        this.textFieldUserId = (TextField)this.scene.lookup("#textFieldUserId");
        this.textFieldFactionId = (TextField)this.scene.lookup("#textFieldFactionId");

        this.botClient = new BotClient();
    }


    //
    //drawing is done within extended AnimationTimer
    //without it, drawing to canvas caused problems, such as freeze or flickering from my experience
    //

    private class CanvasAnimationTimer extends AnimationTimer {
        private long previousTimestamp;

        private BotClient botClient;
        private GraphicsContext graphicsContext;

        CanvasAnimationTimer(BotClient botClient, GraphicsContext graphicsContext) {
            this.botClient = botClient;
            this.graphicsContext = graphicsContext;
        }

        @Override
        public void handle(long timestamp) { //nanosekundy
            if ((this.previousTimestamp + 26666666) > timestamp) {
                return; //60fps
            }

            this.previousTimestamp = timestamp;

            //clear out canvas first
            this.graphicsContext.setFill(Color.BLACK);
            this.graphicsContext.fillRect(0 , 0, 608, 350);

            for (Agent agent: this.botClient.getAgents()) {

                if (agent.getAgentType() == AgentType.AGENT_LOCALPLAYER) {

                    if (agent.isInMove()) {
                        this.graphicsContext.setStroke(Color.WHITE);
                        this.graphicsContext.setLineWidth(2);
                        this.graphicsContext.strokeLine(agent.getPositionX() / 35, agent.getPositionY() / 35, agent.getTargetPositionX() / 35, agent.getTargetPositionY() / 35);
                        //System.out.println("agent is in move");
                    }

                    this.graphicsContext.setFill(Color.LIGHTPINK);
                    this.graphicsContext.fillRoundRect(agent.getPositionX() / 35 ,  agent.getPositionY()/ 35, 5,5,5, 5);

                }
            }

            for (Box box : this.botClient.getBoxes()) {
                this.graphicsContext.setFill(Color.YELLOW);
                this.graphicsContext.fillRect(box.getPositionX() / 35 ,  box.getPositionY()/ 35, 3, 3);
                //needs to be scaled
            }
        }
    }


    public void startBot() {
        this.root.getChildren().clear();
        this.minimapCanvas = new Canvas();
        this.minimapCanvas.setWidth(608);
        this.minimapCanvas.setHeight(350);
        this.minimapCanvas.setLayoutX(20);
        this.minimapCanvas.setLayoutY(20);
        this.minimapCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        this.minimapCanvas.getGraphicsContext2D().fillRect(0,0, this.minimapCanvas.getWidth(), this.minimapCanvas.getHeight());
        this.root.getChildren().add(this.minimapCanvas);

        this.botLogic = new ThreadBotLogic(this.textFieldHost.getText(),
                (short)Integer.parseInt(this.textFieldPort.getText()),
                this.textFieldSessionId.getText(),
                Integer.parseInt(this.textFieldUserId.getText()),
                (short)Integer.parseInt(this.textFieldFactionId.getText()),
                this.minimapCanvas,
                this.botClient);
        this.botLogic.start();

        this.CanvasAnimationTimer = new CanvasAnimationTimer(this.botClient, this.minimapCanvas.getGraphicsContext2D());
        this.CanvasAnimationTimer.start();
    }


    private class StartBotButtonOnClick implements EventHandler<ActionEvent> {

        private BotWindow botWindow;
        StartBotButtonOnClick(BotWindow botWindow) {
            this.botWindow = botWindow;
        }

        @Override
        public void handle(ActionEvent t) {
            this.botWindow.startBot();
        }
    }

    private class WindowOnCloseHandler implements EventHandler<WindowEvent> {

        @Override
        public void handle(WindowEvent t) {
            Platform.exit();
            System.exit(0);
        }
    }

    private class MenuItemShowLogOnClick implements EventHandler<ActionEvent> {

        private BotWindow botWindow;

        MenuItemShowLogOnClick(BotWindow botWindow) {
            this.botWindow = botWindow;
        }


        @Override
        public void handle(ActionEvent event) {
            if (this.botWindow.getWindowApplicationLog() == null) {
                this.botWindow.setWindowApplicationLog(new WindowApplicationLog());
                this.botWindow.getWindowApplicationLog().initWindow();
            }
            if (!this.botWindow.getWindowApplicationLog().isVisible()) {
                this.botWindow.getWindowApplicationLog().setVisible(true);
            }
        }
    }
}