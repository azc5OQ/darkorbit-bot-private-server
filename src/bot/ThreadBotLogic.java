package bot;

import bot.networkevent.fromclient.*;
import bot.networkevent.fromserver.*;
import bot.object.Agent;
import bot.object.AgentType;
import bot.object.Box;
import javafx.scene.canvas.Canvas;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.nio.ByteBuffer;

import java.util.concurrent.CopyOnWriteArrayList;


public class ThreadBotLogic extends Thread {

    public ThreadBotLogic(String host, short port, String sessionId, int userId, short factionId, Canvas minimapCanvas, BotClient botClient) {
        this.host = host;
        this.port = port;
        this.sessionId = sessionId;
        this.userId = userId;
        this.factionId = factionId;
        this.minimapCanvas = minimapCanvas;
        this.botClient = botClient;
    }

    private BotClient botClient;

    private Canvas minimapCanvas;

    private CopyOnWriteArrayList<ByteBuffer> messagesToSend;
    private CopyOnWriteArrayList<ByteBuffer> messagesReceived;
    private ThreadReadFromSocket tcpDataReaderThread;
    private ThreadWriteToSocket tcpDataSenderThread;

   // private ThreadProcessReceivedSocketMessages threadProcessServerMessages;

    private String host;
    private short port;
    private short factionId;
    private int userId;
    private String sessionId;
    private Socket socket;

    private boolean isThreadActive = true;


    //agent -> localplayer
    private Box findBonusBoxClosestToAgent(Agent agent) {

        Box result = null;
        int closestFoundDistance = Integer.MAX_VALUE;


        for (Box box : this.botClient.getBoxes()) {
            int travelDistance = Math.abs((int)Math.sqrt(Math.pow(agent.getPositionX() - box.getPositionX(), 2) + Math.pow(agent.getPositionY() - box.getPositionY(), 2)));
            if (travelDistance < closestFoundDistance) {
                closestFoundDistance = travelDistance;
                result = box;
            }
        }

        return result;
    }

    private void chooseTravelDirectionForLocalPlayer(Agent myAgent, int travelPositionX, int travelPositionY) {
        int targetX = travelPositionX;
        int targetY = travelPositionY;

        //
        //needed for knowing when to stop the travel
        //

        if (targetX < myAgent.getPositionX()) {
            myAgent.setTravelDirectionXBackwards(true);
        } else {
            myAgent.setTravelDirectionXBackwards(false);
        }

        if (targetY < myAgent.getPositionY()) {
            myAgent.setTravelDirectionYBackwards(true);
        } else {
            myAgent.setTravelDirectionYBackwards(false);
        }

        myAgent.setInMove(true);

        myAgent.setTargetPositionX(targetX);
        myAgent.setTargetPositionY(targetY);

        //System.out.println("target travel X -> " + targetX);
        //System.out.println("target travel Y -> " + targetY);

        //System.out.println("agent is not null");
        //move client to random location within boundaries of minimap
        ClientMoveEvent moveEvent = new ClientMoveEvent(targetX, targetY);
        this.messagesToSend.add(moveEvent.getNetworkBuffer());


        //poznamka, ked sa pracuje s goniometrickymi funkciami a pocitaju sa dlzky stran, daj dlzku strany trojuholnika do absolutnej hodnoty, nech nieje zaporna a nevychazdaju zle vysledky
        int travelDistance = Math.abs((int)Math.sqrt(Math.pow(myAgent.getPositionX() - myAgent.getTargetPositionX(), 2) + Math.pow(myAgent.getPositionY() - myAgent.getTargetPositionY(), 2)));

        //System.out.println("travel distance -> " + travelDistance);

        myAgent.setTravelDistance(travelDistance);

        float protilahla = Math.abs((float)myAgent.getPositionY() - (float)myAgent.getTargetPositionY());
        float travelAngleSine = (protilahla) / (float)travelDistance; //travelDistance-> hypotenouse , //myAgent.getPositionY() - targetY opposite side

        //System.out.println("travelAngleSine -> " + travelAngleSine);
        myAgent.setTravelAngleSine(travelAngleSine);
        //System.out.println(travelAngleSine);

        //v jave, ak ma byt vysledkom matematickej operacie cislo s pohyblivou desatinnou ciarkou (double,float) je potrebne explicitne ocastovat datove typy int na float alebo double
        //inak sa moze stat ze vysledok nebude davat zmysel.

        float prilahla = Math.abs((float)myAgent.getPositionX() - (float)targetX);

        float travelAngleCosine = (prilahla) / (float)travelDistance; //travelDistance-> hypotenouse , //myAgent.getPositionX() - targetX adjacent side
        myAgent.setTravelAngleCosine(travelAngleCosine);
        //System.out.println(travelAngleCosine);

        //System.out.println("travel travelAngleCoSine -> " +  Utility.doubleToString(travelAngleCosine));
    }

    private void chooseNewRandomTravelDirectionForLocalPlayer(Agent myAgent) {
        int targetX = Utility.getRandomNumberInRange(1, 20899);
        int targetY = Utility.getRandomNumberInRange(1, 12979);

        //
        //needed for knowing when to stop the travel
        //

        if (targetX < myAgent.getPositionX()) {
            myAgent.setTravelDirectionXBackwards(true);
        } else {
            myAgent.setTravelDirectionXBackwards(false);
        }

        if (targetY < myAgent.getPositionY()) {
            myAgent.setTravelDirectionYBackwards(true);
        } else {
            myAgent.setTravelDirectionYBackwards(false);
        }

        myAgent.setInMove(true);
        myAgent.setTargetPositionX(targetX);
        myAgent.setTargetPositionY(targetY);

        //System.out.println("target travel X -> " + targetX);
        //System.out.println("target travel Y -> " + targetY);

        //System.out.println("agent is not null");
        //move client to random location within boundaries of minimap

        ClientMoveEvent moveEvent = new ClientMoveEvent(targetX, targetY);
        this.messagesToSend.add(moveEvent.getNetworkBuffer());

        //poznamka, ked sa pracuje s goniometrickymi funkciami a pocitaju sa dlzky stran, daj dlzku strany trojuholnika do absolutnej hodnoty, nech nieje zaporna a nevychazdaju zle vysledky
        int travelDistance = Math.abs((int)Math.sqrt(Math.pow(myAgent.getPositionX() - myAgent.getTargetPositionX(), 2) + Math.pow(myAgent.getPositionY() - myAgent.getTargetPositionY(), 2)));

        //System.out.println("travel distance -> " + travelDistance);

        myAgent.setTravelDistance(travelDistance);

        float protilahla = Math.abs((float)myAgent.getPositionY() - (float)myAgent.getTargetPositionY());
        float travelAngleSine = (protilahla) / (float)travelDistance; //travelDistance-> hypotenouse , //myAgent.getPositionY() - targetY opposite side

        //System.out.println("travelAngleSine -> " + travelAngleSine);
        myAgent.setTravelAngleSine(travelAngleSine);
        //System.out.println(travelAngleSine);

        //v jave, ak ma byt vysledkom matematickej operacie cislo s pohyblivou desatinnou ciarkou (double,float) je potrebne explicitne ocastovat datove typy int na float alebo double
        //inak sa moze stat ze vysledok nebude davat zmysel.

        float prilahla = Math.abs((float)myAgent.getPositionX() - (float)targetX);

        float travelAngleCosine = (prilahla) / (float)travelDistance; //travelDistance-> hypotenouse , //myAgent.getPositionX() - targetX adjacent side
        myAgent.setTravelAngleCosine(travelAngleCosine);
        //System.out.println(travelAngleCosine);

        //System.out.println("travel travelAngleCoSine -> " +  Utility.doubleToString(travelAngleCosine));
    }

    @Override
    public void run() {
        //Logger.getInstance().log("|"+ this.threadName +"| ThreadReadFromSocket started");
        Socket socket = null;
        try {
            this.socket = new Socket(this.host, this.port);

            this.messagesToSend = new CopyOnWriteArrayList<>();
            this.messagesReceived = new CopyOnWriteArrayList<>();

            this.tcpDataReaderThread = new ThreadReadFromSocket(this.socket, this.messagesReceived);
            this.tcpDataReaderThread.setThreadName("ClientMain");
            this.tcpDataReaderThread.start();

            this.tcpDataSenderThread = new ThreadWriteToSocket(this.socket, this.messagesToSend);
            this.tcpDataSenderThread.setThreadName("ClientMain");
            this.tcpDataSenderThread.start();

            ClientLoginEvent a = new ClientLoginEvent(this.userId, this.sessionId, (short) this.factionId, 563);
            this.messagesToSend.add(a.getNetworkBuffer());


            //relevant
            //DisposeBoxCommanda
            //CreateBoxCommand


            while (this.isThreadActive) {
                try {
                    Thread.sleep(100); //aby nam vlakno CPU neusmazilo
                    //  alternative for (int i = this.messagesToSend.size() - 1; i >= 0; i--) {

                    //
                    //check if there are any bonus boxes around, if not make a move request to random position
                    //

                    this.messagesReceived.forEach(singleMessage -> {

                        try {
                            //length is not part of buffer, only used within THreadReadFromSocket

                            singleMessage.flip(); //prepne sa mod buffera, zo zapisovania do citania

                            short commandType = 0;
                            try {
                                commandType = singleMessage.getShort();
                            } catch (Exception ex) {
                                System.out.println("|Exception caught|  short commandType = singleMessage.getShort(); " + ex.getMessage());

                            }

                            if (commandType != 0) {
                                System.out.println("eventtype = =" + commandType);

                                if (commandType == 29819) {
                                    ServerMoveEvent serverMoveEvent = new ServerMoveEvent(singleMessage);
                                } else if (commandType == 4224) {
                                    ServerLegacyModuleEvent serverLegacyModuleEvent = new ServerLegacyModuleEvent(singleMessage);
                                } else if (commandType == 18425) {
                                    if (this.botClient.isLoggedIn() == true) {
                                        ServerCreateBoxEvent serverCreateBoxEvent = new ServerCreateBoxEvent(singleMessage);
                                        Box box = new Box(serverCreateBoxEvent.getxPosition(), serverCreateBoxEvent.getyPosition(), serverCreateBoxEvent.getType(), serverCreateBoxEvent.getHash());
                                        this.botClient.getBoxes().add(box);

                                        Agent myAgent = this.botClient.getAgentByUserId(this.botClient.getUserId());

                                        if (myAgent.isMovingToCollectBox() == false) {
                                            myAgent.setMovingToCollectBox(true);
                                            myAgent.setInMove(true);
                                            this.botClient.setBoxToCollectHash(box.getHash());
                                            this.chooseTravelDirectionForLocalPlayer(botClient.getAgentByUserId(botClient.getUserId()), box.getPositionX(), box.getPositionY());
                                        }
                                    }
                                } else if (commandType == 7270) {
                                    ServerShipCreateEvent serverShipCreateEvent = new ServerShipCreateEvent(singleMessage);
                                    System.out.println("serverShipCreateEvent.getUsername() " + serverShipCreateEvent.getUsername());
                                } else if (commandType == 7511) {

                                    //if login request is sent again by bot client, server will not send 7511 network event again, that means bot iwll not be able to obtain accurate position
                                    ServerShipInitializationEvent serverShipInitializationEvent = new ServerShipInitializationEvent(singleMessage);
                                    this.botClient.setPositionX(serverShipInitializationEvent.getPositionX());
                                    this.botClient.setPositionX(serverShipInitializationEvent.getPositionX());
                                    this.botClient.setSpeed(serverShipInitializationEvent.getSpeed());
                                    this.botClient.setUridium((int) serverShipInitializationEvent.getUridium());
                                    this.botClient.setCredits((int) serverShipInitializationEvent.getCredits());
                                    this.botClient.setUsername(serverShipInitializationEvent.getUsername());
                                    this.botClient.setUserId(serverShipInitializationEvent.getUserID());

                                    this.botClient.setLoggedIn(true);

                                    //System.out.println("ship spawn position X " + serverShipInitializationEvent.getPositionX());
                                    //System.out.println("ship spawn position Y " + serverShipInitializationEvent.getPositionY());

                                    Agent agent = new Agent(serverShipInitializationEvent.getUserID(),
                                            serverShipInitializationEvent.getPositionX(),
                                            serverShipInitializationEvent.getPositionY(),
                                            serverShipInitializationEvent.getHp(),
                                            serverShipInitializationEvent.getSpeed(),
                                            AgentType.AGENT_LOCALPLAYER);


                                    //System.out.println("adding agent with ID -> " + agent.getAgentId());
                                    this.botClient.getAgents().add(agent);
                                    //bounds
                                    //X -> 1 do 20899
                                    //Y -> 1 do 12979

                                    Agent myAgent = this.botClient.getAgentByUserId(this.botClient.getUserId());
                                    if (myAgent != null) {
                                        this.chooseNewRandomTravelDirectionForLocalPlayer(myAgent);
                                    }
                                }
                            }

                        } catch(Exception e){
                            e.printStackTrace();
                            StringWriter sw = new StringWriter();
                            e.printStackTrace(new PrintWriter(sw));
                            String exceptionAsString = sw.toString();
                            Logger.getInstance().log("|ThreadProcessClientMessages | error parsing received message:: " + exceptionAsString);
                        }
                        this.messagesReceived.remove(singleMessage);

                    });


                    //
                    //all received messages at this point in code are now processed, time to update players position
                    //

                    if (this.botClient.isLoggedIn()) {

                        Agent agent = this.botClient.getAgentByUserId(this.botClient.getUserId());

                        if (agent != null) {
                            if (agent.isInMove()) {
                                //System.out.println("[!] agent is not null");

                                //sinus alpha - > protilahla ku prepone
                                //protilahla -> sinus alpha * prepona

                                float speedFactor = agent.getSpeed() / 10;

                                int newTravelDistance = agent.getTravelDistance() - (int) speedFactor;

                                //System.out.println("newTravelDistance " + newTravelDistance);

                                agent.setTravelDistance(newTravelDistance);

                                float protilahla = (float) (agent.getTravelAngleSine() * agent.getTravelDistance());
                                float prilahla = (float) (agent.getTravelAngleCosine() * agent.getTravelDistance());

                                //System.out.println("adjacent (used for X) " + prilahla);
                                //System.out.println("opposite (used for Y) " + protilahla);

                                float differenceY = Math.abs(agent.getPositionY() - agent.getTargetPositionY());
                                //System.out.println("differenceY " + differenceY);

                                float yDifferenceToAddToCurrentYPosition = differenceY - protilahla;
                                //System.out.println("(yDifferenceToAddToCurrentYPosition) differenceY - protilahla " + yDifferenceToAddToCurrentYPosition);

                                float rozdielx = Math.abs(agent.getPositionX() - agent.getTargetPositionX());
                                //System.out.println("rozdielx " + rozdielx);

                                float xDifferenceToAddToCurrentXPosition = rozdielx - prilahla;
                                //System.out.println("(xDifferenceToAddToCurrentXPosition)rozdielx - prilahla " + xDifferenceToAddToCurrentXPosition);

                                if (agent.isTravelDirectionXBackwards()) {
                                    xDifferenceToAddToCurrentXPosition = -xDifferenceToAddToCurrentXPosition;
                                }

                                if (agent.isTravelDirectionYBackwards()) {
                                    yDifferenceToAddToCurrentYPosition = -yDifferenceToAddToCurrentYPosition;
                                }

                                //System.out.println("yDifferenceToAddToCurrentYPosition " + yDifferenceToAddToCurrentYPosition);
                                //System.out.println("xDifferenceToAddToCurrentXPosition " + xDifferenceToAddToCurrentXPosition);

                                float xPositionNew = agent.getPositionX() + xDifferenceToAddToCurrentXPosition;
                                float yPositionNew = agent.getPositionY() + yDifferenceToAddToCurrentYPosition;

                                //System.out.println("xPositionNew " + xPositionNew);
                                //System.out.println("yPositionNew " + yPositionNew);

                                agent.setPositionX((int) xPositionNew);
                                agent.setPositionY((int) yPositionNew);


                                boolean isTargetDestinationReached = ((agent.isTravelDirectionXBackwards() && xPositionNew <= agent.getTargetPositionX()) ||
                                        (!agent.isTravelDirectionXBackwards() && xPositionNew >= agent.getTargetPositionX()));

                                if (isTargetDestinationReached) {

                                    //System.out.println("setting agent in move to false");
                                    agent.setInMove(false);
                                    agent.setPositionX(agent.getTargetPositionX());
                                    agent.setPositionY(agent.getTargetPositionY());

                                    //
                                    //if agent was moving to destination to collect box, make the collect now
                                    //

                                    if (agent.isMovingToCollectBox()) {
                                        ClientCollectBoxRequestEvent clientCollectBoxRequestEvent = new ClientCollectBoxRequestEvent(this.botClient.getBoxToCollectHash());
                                        this.messagesToSend.add(clientCollectBoxRequestEvent.getNetworkBuffer());
                                        Thread.sleep(150);

                                        agent.setMovingToCollectBox(false);
                                        this.botClient.deleteBoxFromCopyOnWriteArrayListByHash(this.botClient.getBoxToCollectHash());
                                        this.botClient.setBoxToCollectHash("");
                                    }


                                    //try to find new box
                                    Box box = this.findBonusBoxClosestToAgent(agent);

                                    if (box != null) {
                                        agent.setMovingToCollectBox(true);
                                        agent.setInMove(true);
                                        this.botClient.setBoxToCollectHash(box.getHash());
                                        this.chooseTravelDirectionForLocalPlayer(botClient.getAgentByUserId(botClient.getUserId()), box.getPositionX(), box.getPositionY());
                                    } else {
                                        this.chooseNewRandomTravelDirectionForLocalPlayer(agent);
                                    }
                                }


                                //float yPositionNew = agent.getPositionY() + protilahla;
                                //System.out.println("xPositionNew " + xPositionNew);
                                // System.out.println("yPositionNew " + yPositionNew);
                                agent.setPositionX((int) xPositionNew);
                                //agent.setPositionY((int)yPositionNew);
                            }
                        }
                    }
                } catch(Exception e){
                    e.printStackTrace();
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsString = sw.toString();
                    Logger.getInstance().log("|JChatServer ThreadProcessClientMessages | exception caught: " + exceptionAsString);
                }
            }
        } catch (IOException e) {
            Logger.getInstance().log(e.getMessage());
        }
    }
}
