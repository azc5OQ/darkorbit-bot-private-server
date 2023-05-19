package bot.networkevent.fromclient;


import bot.networkevent.NetworkEvent;

import java.nio.ByteBuffer;

public class ClientMoveEvent extends NetworkEvent {


    private int targetPositionX;
    private int targetPositionY;

    public short ID = 17063;


    //emulator im making this bot for, the server emulator, does not relaly need current X and Y positions to be valid but it sitll needs it to be sent, (official darkorbit server might have needed current X and Y aswell) only target X and Y is required
    public ClientMoveEvent(int targetPositionX, int targetPositionY)
    {
        this.targetPositionX = targetPositionX;
        this.targetPositionY = targetPositionY;

        this.prepareNetworkDataBufferForSending();
    }

    private void prepareNetworkDataBufferForSending()
    {
        int a = this.targetPositionY << 24;
        int b = this.targetPositionY >>> 8;
        int targetPositionYShifted = a | b;

        a = this.targetPositionX << 4;
        b = this.targetPositionX >>> 28;
        int targetPositionXShifted = a | b;


        short networkMessageLength = (short)(18);

        this.writeShort(networkMessageLength); //2
        this.writeShort(this.ID); //2

        this.writeIntBE(targetPositionYShifted);
        this.writeIntBE(1111111111);// is not validated by server, this is something client sends for the original server from BP
        this.writeShort((short)1111);
        this.writeIntBE(targetPositionXShifted);
        this.writeIntBE(1111111111); // is not validated by server
    }
}