package bot.networkevent.fromclient;


import bot.networkevent.NetworkEvent;

public class ClientLoginEvent extends NetworkEvent {

    private int userID;
    private String sessionID;

    private short factionID;
    private int instanceID;
    public short ID = 10996;



    public ClientLoginEvent(int userID, String sessionID, short factionID, int instanceID)
    {
        this.userID = userID;
        this.sessionID = sessionID;
        this.factionID = factionID;
        this.instanceID = instanceID;
        this.prepareNetworkDataBufferForSending();
    }

    private void prepareNetworkDataBufferForSending()
    {
        short networkMessageLength = (short)(this.sessionID.length() + this.version.length() + 16);
        this.writeShort(networkMessageLength);
        this.writeShort(this.ID);
        int instanceId = 563;
        //this.writeInt(instanceId); //4

        //nerozumiem co je instance ID. V kazdom pripade, nemeni sa. tieto 4 bajty predstavuju hodnotu 563, ktora sa jaksi, cez javu, nedokazala spravne odoslat, bolo potrebne bajty zobrat z requestu a stanovit ich na pevni.
        //neposobi to tak, ze by sa tato hodnota menila.

        this.writeByte((byte)51); //4
        this.writeByte((byte)0); //4
        this.writeByte((byte)0); //4
        this.writeByte((byte)2); //4


        this.writeIntLE(this.userID); //4
        this.writeShort(this.factionID); //2
        this.writeString(this.sessionID);
        this.writeString(this.version);
    }
}