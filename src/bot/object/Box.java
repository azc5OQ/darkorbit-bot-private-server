package bot.object;

public class Box {
    private int positionX;
    private int positionY;
    private String type;

    private String hash;

    public String getHash() {
        return this.hash;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public String getType() {
        return this.type;
    }

    public Box(int positionX, int positionY, String type, String hash) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.type = type;
        this.hash = hash;
    }
}
