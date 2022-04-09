package bgu.spl.net;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;
    private final byte[] opBytes = {-1,-1};
    int zeroCounter = 0;
    int opcode;

    @Override
    public String decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (opBytes[0]==-1)
            opBytes[0]=nextByte;
        else if(opBytes[1]==-1) {  //once we have this byte- we can know the opcode
            opBytes[1] = nextByte;
            opcode = bytesToShort(opBytes);
            if (opcode == 4 || opcode == 11)  //if the opcode is 4 or 11 there is no more bytes for next- return the opcode
                return decodeMessage();
        }
        else {   //opcode already found- message continue
            pushByte(nextByte);
            if (nextByte == '\0')
                zeroCounter++;
            if (opcode == 1 || opcode == 2 || opcode == 3)
                if (zeroCounter == 2)
                    return decodeMessage();
            if (opcode == 5 || opcode == 6 || opcode == 7 || opcode == 8 || opcode == 9 || opcode == 10)
                if (zeroCounter == 1)
                    return decodeMessage();
        }
        return null; //not a line yet
    }

    @Override
    public byte[] encode(String message) {
        short opcode;
        byte[] opcodeToClient;
        byte[] cmdOpcodeToClient;
        byte[] messageToClient;
        if(message.startsWith("ERROR"))
            opcode = 13;
        else
            opcode = 12;
        message=message.substring(message.indexOf(' ')+1);
        String commandOpcode;
        if(message.indexOf('\n')!=-1){
            commandOpcode = message.substring(0, message.indexOf('\n'));
            message = message.substring(message.indexOf('\n'));
        }
        else if(message.indexOf(' ')!=-1) {
            commandOpcode = message.substring(0, message.indexOf(' '));
            message=message.substring(message.indexOf(' '));
        }
        else{
            commandOpcode = message;
            message = "";
        }
        int cmdOpcode=Integer.decode(commandOpcode);
        opcodeToClient=shortToBytes(opcode);
        cmdOpcodeToClient=shortToBytes((short)cmdOpcode);
        messageToClient=(message + "\0").getBytes();
        byte[] totalBytesToClient=new byte[opcodeToClient.length+cmdOpcodeToClient.length+messageToClient.length];
        System.arraycopy(opcodeToClient,0,totalBytesToClient,0,opcodeToClient.length);
        System.arraycopy(cmdOpcodeToClient,0,totalBytesToClient,opcodeToClient.length,cmdOpcodeToClient.length);
        System.arraycopy(messageToClient,0,totalBytesToClient,opcodeToClient.length+cmdOpcodeToClient.length,messageToClient.length);
        return totalBytesToClient;
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }
    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }

    public short bytesToShort(byte[] byteArr) { //Decode 2 bytes to short
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

    public byte[] shortToBytes(short num) // Encode short to 2 bytes
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }

    private String decodeMessage(){
        opBytes[0] = -1;  //initialize
        opBytes[1] = -1;  //decoder
        zeroCounter=0;    //parameters
        String message=(popString()).replace('\0', ' ');
        if (!message.equals(""))  //for the cases of opcode 4 or 11 - the message will be empty
            message=message.substring(0,message.length()-1);
        return opcode + " " + message;
    }
}
