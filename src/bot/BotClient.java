package bot;

import bot.object.Agent;
import bot.object.Box;

import java.nio.ByteBuffer;
import java.util.concurrent.CopyOnWriteArrayList;

public class BotClient {
    private int uridium;
    private int credits;
    private int positionX;
    private int positionY;

    private String username;
    private int userId;

    private String boxToCollectHash;

    public String getBoxToCollectHash() {
        return this.boxToCollectHash;
    }

    public void setBoxToCollectHash(String boxToCollectHash) {
        this.boxToCollectHash = boxToCollectHash;
    }

    private boolean isTravelingToCollectBox = false;

    public boolean isTravelingToCollectBox() {
        return this.isTravelingToCollectBox;
    }

    public void setTravelingToCollectBox(boolean travelingToCollectBox) {
        this.isTravelingToCollectBox = travelingToCollectBox;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int speed;

    private boolean isLoggedIn;

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    private int rangeVisibility;

    public int getUridium() {
        return this.uridium;
    }

    public void setUridium(int uridium) {
        this.uridium = uridium;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRangeVisibility() {
        return this.rangeVisibility;
    }

    public void setRangeVisibility(int rangeVisibility) {
        this.rangeVisibility = rangeVisibility;
    }

    private CopyOnWriteArrayList<Agent> agents;
    private CopyOnWriteArrayList<Box> boxes;


    public Agent getAgentByUserId(int userId) {

        Agent result = null;

        for (Agent agent : this.agents) {
            if (agent.getAgentId() == userId) {
                result = agent;
                break;
            }
        }

        return result;
    }

    public void deleteBoxFromCopyOnWriteArrayListByHash(String hash) {

        this.boxes.forEach(singleBox -> {
            try {
                if (singleBox.getHash().equals(hash)) {
                    this.boxes.remove(singleBox);
                }
            } catch (Exception ex ) {
                Logger.getInstance().log(ex.getMessage());
            }
        });
    }

    public CopyOnWriteArrayList<Agent> getAgents() {
        return agents;
    }

    public CopyOnWriteArrayList<Box> getBoxes() {
        return boxes;
    }

    public BotClient() {
        this.boxes = new CopyOnWriteArrayList<>();
        this.agents = new CopyOnWriteArrayList<>();
    }
}
