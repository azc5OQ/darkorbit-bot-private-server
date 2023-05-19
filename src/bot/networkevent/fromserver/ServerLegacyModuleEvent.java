package bot.networkevent.fromserver;

import bot.networkevent.NetworkEvent;

import java.nio.ByteBuffer;

//basically, this is a event that contains another sub-event that further specifies what to do
//legacy because it was made in earlier version of DO? before they decided to assign each event a ID?

public class ServerLegacyModuleEvent extends NetworkEvent {
    public short ID = 4224;

    private String packet;
    public ServerLegacyModuleEvent(ByteBuffer buffer)
    {
        this.setNetworkDataBuffer(buffer);
        String receivedPacket = this.readString(); //lol
        //System.out.println("ServerLegacyModuleEvent : " + receivedPacket);
    }
}