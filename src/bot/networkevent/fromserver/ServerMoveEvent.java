package bot.networkevent.fromserver;

import bot.Utility;
import bot.networkevent.NetworkEvent;

import java.nio.ByteBuffer;

public class ServerMoveEvent extends NetworkEvent {

    public short ID = 29819;
    public int userId;
    public int x;
    public int y;
    public int timeToTarget;

    public ServerMoveEvent(ByteBuffer buffer)
    {
        this.setNetworkDataBuffer(buffer);
        //poucenie, ked sa konstruje nejaka cislena hodnota z viacerych bajtov, vediet aky "endianitu" system pouziva, aby cislo nemalo nejaku divnu hodnotu

        short someValue = buffer.getShort(); // this value should be 6455
        //C# emulator uses big endian orderning for int values that are to be sent over network, for this java bot, that means, receveived network bytes can be interpreted as ints "as they are" without reversing the order of bytes (java also uses BE)

        int yPositionShifted = buffer.getInt();
        int userIdShifted = buffer.getInt();
        int xPositionShifted = buffer.getInt();
        int timeToTargetShifted = buffer.getInt();

        int a = yPositionShifted << 13;
        int b = yPositionShifted >>> 19;
        int yPosition = a | b;

        a = userIdShifted << 12;
        b = userIdShifted >>> 20;
        int userId = a | b;

        a = xPositionShifted << 4;
        b =xPositionShifted >>> 28;
        int xPosition = a | b;

        a = timeToTargetShifted << 13;
        b = timeToTargetShifted >>> 19;
        int timeToTarget = a | b;

        if (userIdShifted == 1) {
            System.out.println("x -> " + xPosition + " y -> "+ yPosition + " userId -> " + userId + "  timeToTarget -> " + timeToTarget);
        }
    }
}