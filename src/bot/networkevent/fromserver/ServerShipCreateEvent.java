package bot.networkevent.fromserver;

import bot.networkevent.NetworkEvent;

import java.nio.ByteBuffer;

//this is equivalent to ServerShipInitializationEvent, sometimes ServerShipInitializaitoNEventisNotSend, Server ShipCreateEvent is always sent
public class ServerShipCreateEvent extends NetworkEvent {

    public short ID = 7270;

    private int factionId;
    private String shipType;
    private int maxCargo;
    private int maxNanoHull;
    private int level;
    private byte cloaked;
    private int speed;
    private double uridium;

    private int mapId;

    private double credits;

    private int shield;
    private int nanoHull;
    private String username;

    private int positionX;
    private int positionY;

    private double experience;
    private byte premium;

    private String clanTag;
    private int cargo;
    private int maxShield;
    private int maxHp;
    private int userID;

    private int expansionStage;
    private int rings;
    private double honor;

    private int pClanID;
    private int rankID;


    private float jackpot;
    private int hp;
    private int modifiersCount;


    public short getID() {
        return ID;
    }

    public int getFactionId() {
        return factionId;
    }

    public String getShipType() {
        return shipType;
    }

    public int getMaxCargo() {
        return maxCargo;
    }

    public int getMaxNanoHull() {
        return maxNanoHull;
    }

    public int getLevel() {
        return level;
    }

    public byte getCloaked() {
        return cloaked;
    }

    public int getSpeed() {
        return speed;
    }

    public double getUridium() {
        return uridium;
    }

    public int getMapId() {
        return mapId;
    }

    public double getCredits() {
        return credits;
    }

    public int getShield() {
        return shield;
    }

    public int getNanoHull() {
        return nanoHull;
    }

    public String getUsername() {
        return username;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public double getExperience() {
        return experience;
    }

    public byte getPremium() {
        return premium;
    }

    public String getClanTag() {
        return clanTag;
    }

    public int getCargo() {
        return cargo;
    }

    public int getMaxShield() {
        return maxShield;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getUserID() {
        return userID;
    }

    public int getExpansionStage() {
        return expansionStage;
    }

    public int getRings() {
        return rings;
    }

    public double getHonor() {
        return honor;
    }

    public int getpClanID() {
        return pClanID;
    }

    public int getRankID() {
        return rankID;
    }

    public float getJackpot() {
        return jackpot;
    }

    public int getHp() {
        return hp;
    }

    public int getModifiersCount() {
        return modifiersCount;
    }

    public ServerShipCreateEvent(ByteBuffer buffer) {
        this.setNetworkDataBuffer(buffer);

       this.username = this.readString();
       int galaxyGatesDone = buffer.getInt(); //;galaxyGatesDone
       byte useSystemFont = buffer.get(); //useSystemFont
       int factionId = buffer.getInt(); //factionId
       int expansionStage = buffer.getInt(); //expansionStage
       this.shipType = this.readString(); //shipType
       int dailyRank = buffer.getInt(); //dailyRank
       byte isNpc = buffer.get(); //writeBoolean npc
       int int71 = buffer.getInt(); //clanId
       int int18 = buffer.getInt(); //clandimpoloacy



        //param1.writeUTF(userName);
        //param1.writeInt(galaxyGatesDone >> 13 | galaxyGatesDone << 19);
        //param1.writeBoolean(useSystemFont);
        //param1.writeInt(factionId >> 7 | factionId << 25);
        //param1.writeInt(expansionStage << 6 | expansionStage >> 26);
        //param1.writeUTF(shipType);
        //param1.writeInt(dailyRank >> 4 | dailyRank << 28);
        //param1.writeBoolean(npc);
        //param1.writeInt(clanId << 3 | clanId >> 29);
        //param1.write(clanDiplomacy.write());
        //param1.writeBoolean(warnBox);
        //param1.writeInt(motherShipId >> 16 | motherShipId << 16);
        //param1.writeUTF(clanTag);
        //param1.writeInt(modifier.Count);
        //foreach(var m in modifier)
        //{
        //    param1.write(m.write());
        //}
        //param1.writeBoolean(cloaked);
        //param1.writeInt(userId >> 14 | userId << 18);
        //param1.writeInt(positionIndex << 3 | positionIndex >> 29);
        //param1.writeInt(x << 4 | x >> 28);
        //param1.write(var11d.write());
        //param1.writeInt(y << 9 | y >> 23);

        int factionIdShifted = buffer.getInt();
        this.factionId = factionIdShifted >>> 12 | factionIdShifted << 20;
        //System.out.println("ServerShipInitializationEvent factionId -> " + this.factionId);


        int hpShifted = buffer.getInt();
        this.hp = hpShifted << 29 | hpShifted >>> 3;
        //System.out.println("ServerShipInitializationEvent hp -> " + this.hp);


        int modifiersCount = buffer.getInt(); //not shifted amzing
        System.out.println("ServerShipInitializationEvent modifiersCount -> " + modifiersCount);


        //BEWARE
        // param1.writeInt(pModifiers.Count);
        // foreach(var modifier in pModifiers)
        // {
        //     param1.write(modifier.write());
        // }

        int yPositionShifted = buffer.getInt();
        this.positionY = yPositionShifted << 31 | yPositionShifted >>> 1;
        //System.out.println("ServerShipInitializationEvent positionY -> " + this.positionY);

        // param1.writeInt(y << 1 | y >> 31); //they trolled bot devleopers by sending this at last
        // param1.writeBoolean(unknown2);

    }
}
