package bot.networkevent.fromclient;


import bot.networkevent.NetworkEvent;

import java.nio.ByteBuffer;

public class ClientCollectBoxRequestEvent extends NetworkEvent {

    public short ID = 6532;
    private String boxHash;
    //emulator im making this bot for, the server emulator, does not really need current X and Y positions to be valid but it sitll needs it to be sent, (official darkorbit server might have needed current X and Y aswell) only target X and Y is required

    public ClientCollectBoxRequestEvent(String boxHash)
    {
        this.boxHash = boxHash;
        this.prepareNetworkDataBufferForSending();
    }

    private void prepareNetworkDataBufferForSending()
    {
        //ID of message and length itself is not taken into account when calculating value of networkMessageLength
        short networkMessageLength = (short)(18 + boxHash.length());
        this.writeShort(networkMessageLength); //2
        this.writeShort(this.ID); //2

        this.writeIntBE(1111111);
        this.writeIntBE(1111111);
        this.writeIntBE(1111111);
        this.writeIntBE(1111111);

        this.writeString(this.boxHash);
    }
}