package bot.networkevent.fromserver;

import bot.networkevent.NetworkEvent;

import java.nio.ByteBuffer;

public class ServerCreateBoxEvent extends NetworkEvent {
    public short ID = 18425;

    private String type;
    private int xPosition;
    private int yPosition;

    private String hash;

    public String getHash() {
        return hash;
    }

    public String getType() {
        return this.type;
    }

    public int getxPosition() {
        return this.xPosition;
    }

    public int getyPosition() {
        return this.yPosition;
    }

    public ServerCreateBoxEvent(ByteBuffer buffer)
    {
        this.setNetworkDataBuffer(buffer);
        this.hash = this.readString(); //lol

        //param1.writeUTF(hash);
        //param1.writeInt(y >> 9 | y << 23);
        //param1.writeInt(x >> 4 | x << 28);
        //param1.writeUTF(type);
        //param1.writeShort(21295);

        int yPositionShifted = buffer.getInt(); // this value should be 6455
        int xPositionShifted = buffer.getInt(); // this value should be 6455

        int a = yPositionShifted << 9;
        int b = yPositionShifted >>> 23;
        int yPosition = a | b;

        a = xPositionShifted << 4;
        b = xPositionShifted >>> 28;
        int xPosition = a | b;


        String type = this.readString(); //lol

        //System.out.println("ServerCreateBoxEvent hash -> "+hash+" xPosition  " + xPosition + " yPosition  "+ yPosition + " type " + type);

        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;


        //ServerCreateBoxEvent hash -> gNuw0JBxrE xPosition  18285 yPosition  3988 type BONUS_BOX
        //ServerCreateBoxEvent hash -> JAPy9uiiTp xPosition  17362 yPosition  3788 type BONUS_BOX
        //ServerCreateBoxEvent hash -> DuFjcISPKE xPosition  15897 yPosition  3053 type BONUS_BOX
        //ServerCreateBoxEvent hash -> PqTCEvni2s xPosition  16769 yPosition  5016 type BONUS_BOX
        //ServerCreateBoxEvent hash -> UsUsj8rY3O xPosition  15266 yPosition  4375 type BONUS_BOX
        //ServerCreateBoxEvent hash -> uZvyPY67WH xPosition  14666 yPosition  4332 type BONUS_BOX
        //ServerCreateBoxEvent hash -> A8Zep7BZjP xPosition  13820 yPosition  5110 type BONUS_BOX
        //ServerCreateBoxEvent hash -> xCKhRijdYW xPosition  13463 yPosition  4452 type BONUS_BOX
        //ServerCreateBoxEvent hash -> axRopWrMfV xPosition  14435 yPosition  6024 type BONUS_BOX

    }
}