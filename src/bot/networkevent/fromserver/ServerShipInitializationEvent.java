package bot.networkevent.fromserver;

import bot.networkevent.NetworkEvent;

import java.nio.ByteBuffer;

public class ServerShipInitializationEvent extends NetworkEvent {

    public short ID = 7511;

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

    public ServerShipInitializationEvent(ByteBuffer buffer) {
        this.setNetworkDataBuffer(buffer);


        int factionIdShifted = buffer.getInt();
        this.factionId = factionIdShifted >>> 12 | factionIdShifted << 20;
        //System.out.println("ServerShipInitializationEvent factionId -> " + this.factionId);

        this.shipType = this.readString();
        //System.out.println("ServerShipInitializationEvent shipType -> " + this.shipType);

        byte unknownboolean = buffer.get();
        short unknownshort = buffer.getShort();

        int maxCargoShifted = buffer.getInt();
        this.maxCargo = maxCargoShifted << 16 | maxCargoShifted >>> 16;
        //System.out.println("ServerShipInitializationEvent maxCargo -> " + this.maxCargo);


        int levelShifted = buffer.getInt();
        this.level = levelShifted << 23 | levelShifted >>> 9;
        //System.out.println("ServerShipInitializationEvent level -> " + this.level);


        int maxNanoHullShifted = buffer.getInt();
        this.maxNanoHull = maxNanoHullShifted << 14 | maxNanoHullShifted >>> 18;
        //System.out.println("ServerShipInitializationEvent maxNanoHull -> " + this.maxNanoHull);


        this.cloaked = buffer.get();
        //System.out.println("ServerShipInitializationEvent cloacked -> " + this.cloaked);



        int speedShifted = buffer.getInt();
        this.speed = speedShifted << 10 | speedShifted >>> 22;
        //System.out.println("ServerShipInitializationEvent speed -> " + this.speed);


        this.uridium = buffer.getDouble();
        //System.out.println("ServerShipInitializationEvent uridium -> " + this.uridium);


        int mapIdShifted = buffer.getInt();
        this.mapId = mapIdShifted << 9 | mapIdShifted >>> 23;
        //System.out.println("ServerShipInitializationEvent speed -> " + this.mapId);


        int shieldShifted = buffer.getInt();
        this.shield = shieldShifted << 10 | shieldShifted >>> 22;
        //System.out.println("ServerShipInitializationEvent shield -> " + this.shield);


        int expansionStageShifted = buffer.getInt();
        this.expansionStage = expansionStageShifted << 24 | expansionStageShifted >>> 8;
        //System.out.println("ServerShipInitializationEvent expansionStage -> " + this.expansionStage);


        this.credits = buffer.getDouble();
        //System.out.println("ServerShipInitializationEvent credits -> " + this.credits);


        int nanoHullShifted = buffer.getInt();
        this.nanoHull = nanoHullShifted << 13 | nanoHullShifted >>> 19;
        //System.out.println("ServerShipInitializationEvent nanoHull -> " + this.nanoHull);


        this.username = this.readString();
        //System.out.println("ServerShipInitializationEvent username -> " + this.username);


        int positionXShifted = buffer.getInt();
        this.positionX = positionXShifted << 5 | positionXShifted >>> 27;
        //System.out.println("ServerShipInitializationEvent positionX -> " + this.positionX);

        this.experience = buffer.getDouble();
        //System.out.println("ServerShipInitializationEvent experience -> " + this.experience);


        this.premium = buffer.get();
        //System.out.println("ServerShipInitializationEvent premium -> " + this.premium);

        this.clanTag = this.readString();
        //System.out.println("ServerShipInitializationEvent clanTag -> " + this.clanTag);

        int cargoShifted = buffer.getInt();
        this.cargo = cargoShifted << 10 | cargoShifted >>> 22;
        //System.out.println("ServerShipInitializationEvent cargo -> " + this.cargo);


        int maxShieldShifted = buffer.getInt();
        this.maxShield = maxShieldShifted << 26 | maxShieldShifted >>> 6;
        //System.out.println("ServerShipInitializationEvent maxShield -> " + this.maxShield);

        int maxHpShifted = buffer.getInt();
        this.maxHp = maxHpShifted << 5 | maxHpShifted >>> 27;
        //System.out.println("ServerShipInitializationEvent maxHp -> " + this.maxHp);


        int userIDShifted = buffer.getInt();
        this.userID = userIDShifted << 31 | userIDShifted >>> 1;
        //System.out.println("ServerShipInitializationEvent userID -> " + this.userID);


        int ringsShifted = buffer.getInt();
        this.rings = ringsShifted << 1 | ringsShifted >>> 31;
        //System.out.println("ServerShipInitializationEvent rings -> " + this.rings);


        this.honor = buffer.getDouble();
        //System.out.println("ServerShipInitializationEvent honor -> " + this.honor);



        int clanIdShifted = buffer.getInt();
        this.pClanID = clanIdShifted << 8 | clanIdShifted >>> 24;
        //System.out.println("ServerShipInitializationEvent pClanID -> " + this.pClanID);



        int rankIdShifted = buffer.getInt();
        this.rankID = rankIdShifted << 29 | rankIdShifted >>> 3;
        //System.out.println("ServerShipInitializationEvent rankID -> " + this.rankID);

        this.jackpot = buffer.getFloat();
        //System.out.println("ServerShipInitializationEvent jackpot -> " + this.jackpot);



        int hpShifted = buffer.getInt();
        this.hp = hpShifted << 29 | hpShifted >>> 3;
        //System.out.println("ServerShipInitializationEvent hp -> " + this.hp);


        int modifiersCount = buffer.getInt(); //not shifted amzing
        //System.out.println("ServerShipInitializationEvent modifiersCount -> " + modifiersCount);


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
