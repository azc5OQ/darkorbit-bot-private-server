package bot.object;


//also called mob, npc or ship or player. Unified name is agnet
public class Agent {
    private int positionX;
    private int positionY;
    private int HP;
    private int speed;

    private boolean travelDirectionXBackwards;
    private boolean travelDirectionYBackwards;

    public boolean isTravelDirectionXBackwards() {
        return travelDirectionXBackwards;
    }

    public void setTravelDirectionXBackwards(boolean travelDirectionXBackwards) {
        this.travelDirectionXBackwards = travelDirectionXBackwards;
    }

    public boolean isTravelDirectionYBackwards() {
        return travelDirectionYBackwards;
    }

    public void setTravelDirectionYBackwards(boolean travelDirectionYBackwards) {
        this.travelDirectionYBackwards = travelDirectionYBackwards;
    }

    private int moveDistance;

    public int getMoveDistance() {
        return moveDistance;
    }

    private float travelAngleSine;
    private float travelAngleCosine;

    public float getTravelAngleSine() {
        return travelAngleSine;
    }

    public void setTravelAngleSine(float travelAngleSine) {
        this.travelAngleSine = travelAngleSine;
    }

    public float getTravelAngleCosine() {
        return travelAngleCosine;
    }

    public void setTravelAngleCosine(float travelAngleCosine) {
        this.travelAngleCosine = travelAngleCosine;
    }

    public void setMoveDistance(int moveDistance) {
        this.moveDistance = moveDistance;
    }

    public int getAgentId() {
        return agentId;
    }


    private int agentId;

    private int travelDistance;

    public int getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(int travelDistance) {
        this.travelDistance = travelDistance;
    }

    private AgentType agentType;

    public Agent(int agentId, int positionX, int positionY, int HP, int speed, AgentType agentType) {
        this.agentId = agentId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.HP = HP;
        this.speed = speed;
        this.agentType = agentType;
        this.isInMove = false;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isInMove() {
        return isInMove;
    }

    public void setInMove(boolean inMove) {
        isInMove = inMove;
    }

    public int getTargetPositionX() {
        return targetPositionX;
    }

    public void setTargetPositionX(int targetPositionX) {
        this.targetPositionX = targetPositionX;
    }

    public int getTargetPositionY() {
        return targetPositionY;
    }

    public void setMovingToCollectBox(boolean movingToCollectBox) {
        isMovingToCollectBox = movingToCollectBox;
    }

    public void setTargetPositionY(int targetPositionY) {
        this.targetPositionY = targetPositionY;
    }

    private boolean isInMove;

    private boolean isMovingToCollectBox;

    public boolean isMovingToCollectBox() {
        return isMovingToCollectBox;
    }

    private int targetPositionX;
    private int targetPositionY;

}
