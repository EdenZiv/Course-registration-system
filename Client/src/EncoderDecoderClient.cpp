#include "../include/EncoderDecoderClient.h"
#include <vector>
#include <sstream>
#include <string>
using namespace std;

EncoderDecoderClient::EncoderDecoderClient(): serverMessage(string()), opcodeMap(unordered_map<string,short>()){ // constructor
    opcodeMap["ADMINREG"]=1;
    opcodeMap["STUDENTREG"]=2;
    opcodeMap["LOGIN"]=3;
    opcodeMap["LOGOUT"]=4;
    opcodeMap["COURSEREG"]=5;
    opcodeMap["KDAMCHECK"]=6;
    opcodeMap["COURSESTAT"]=7;
    opcodeMap["STUDENTSTAT"]=8;
    opcodeMap["ISREGISTERED"]=9;
    opcodeMap["UNREGISTER"]=10;
    opcodeMap["MYCOURSES"]=11;
}

short EncoderDecoderClient::bytesToShort(const char *bytes) {
    auto result = (short)((bytes[0] & 0xff) << 8);
    result += (short)(bytes[1] & 0xff);
    return result;
}

void EncoderDecoderClient::shortToBytes(short num, string &bytes) {
    bytes.push_back((num >> 8) & 0xFF);
    bytes.push_back(num & 0xFF);
}

string EncoderDecoderClient::encode(std::string &message) {
    string bytesArr;
    vector<string> tokens; // Vector of string to save tokens
    stringstream stream(message);
    string piece;
    while (getline(stream, piece, ' ')) // Tokenizing w.r.t. space ' '
        tokens.push_back(piece);
    short opcodeNum= opcodeMap[tokens.front()];
    shortToBytes(opcodeNum,bytesArr);
    tokens.erase(tokens.begin());
    for(const string& word:tokens) {
        pushWord(word, bytesArr);
        bytesArr.push_back('\0');
    }
    return bytesArr;
}

string EncoderDecoderClient::decodeOpcodeBytes(char *bytes) {
    short opcode = bytesToShort(bytes);
    short msgOpcode= bytesToShort(bytes+2);
    if (opcode==13)
        serverMessage+="ERROR "+to_string(msgOpcode);
    if(opcode==12) {
        serverMessage += "ACK " + to_string(msgOpcode);
        if (msgOpcode == 6 || msgOpcode == 7 || msgOpcode == 8 || msgOpcode == 9 || msgOpcode == 11)
            return "";
    }
    return serverMessage;
}

string EncoderDecoderClient::decodeAckMsgBytes(const char byte) {
    if (byte!='\0'){
        serverMessage.append(1,byte); //push word not good for here***
        return "";
    }
    return serverMessage;
}

void EncoderDecoderClient::pushWord(const string& word, string& bytes) {
    for (unsigned char ch : word) {
        if (ch < 0x80)
            bytes.push_back(ch);
        else {
            bytes.push_back((0xc0) | (ch >> 6));
            bytes.push_back((0x80) | (ch & 0x3f));
        }
    }
}

void EncoderDecoderClient::cleanServerMsg() {
    serverMessage="";
}




