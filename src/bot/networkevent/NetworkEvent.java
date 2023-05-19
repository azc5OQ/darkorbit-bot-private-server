package bot.networkevent;


import bot.Logger;

import java.nio.ByteBuffer;

public class NetworkEvent {

    public ByteBuffer getNetworkBuffer() {
        return this.data;
    }

    //tato trieda je urobena tak, aby vyhovovala strukture sietovych requestov darko orbite
    protected ByteBuffer data;

    protected String version = "8.3.2";

    public NetworkEvent()
    {
        this.data = ByteBuffer.allocate(0);
    }

    public void setNetworkDataBuffer(ByteBuffer data) {
        this.data = data;
    }

    public void writeByte(byte value) {

        ByteBuffer temp = this.data; //create temp buffer
        this.data = ByteBuffer.allocate(temp.array().length + 1);
        this.data.put(temp.array());
        this.data.put(value);

    }


    public void writeShort(short value) {

        ByteBuffer temp = this.data; //create temp buffer
        this.data = ByteBuffer.allocate(temp.array().length + 2);
        this.data.put(temp.array());
        this.data.putShort(value);
    }

    //eware endianess
    public void writeIntLE(int value) {

        ByteBuffer temp = this.data; //create temp buffer
        this.data = ByteBuffer.allocate(temp.array().length + 4);
        this.data.put(temp.array());


        //big endian to little
        //z enjake dovodu, cislo je little endian ked sa zapise do buffera. Urobi sa z neho big endian. Aj ked java defaulne pouziva big endian
        int valueReversed = Integer.reverseBytes(value);

        this.data.putInt(valueReversed);
    }

    public void writeIntBE(int value) {

        ByteBuffer temp = this.data; //create temp buffer
        this.data = ByteBuffer.allocate(temp.array().length + 4);
        this.data.put(temp.array());


        //big endian to little
        //z enjake dovodu, cislo je little endian ked sa zapise do buffera. Urobi sa z neho big endian. Aj ked java defaulne pouziva big endian

        this.data.putInt(value);
    }

    public void writeString(String value) {
        ByteBuffer temp = this.data; //create temp buffer
        this.data = ByteBuffer.allocate(temp.array().length + 2 + value.length());
        this.data.put(temp.array());
        this.data.putShort((short)value.length()); //string length goes first, as short
        this.data.put(value.getBytes());
    }

    public String readString()
    {
        short length = this.data.getShort();
        byte[] stringBytes = new byte[length];
        this.data.get(stringBytes, 0, length);
        return new String(stringBytes);
    }

}
