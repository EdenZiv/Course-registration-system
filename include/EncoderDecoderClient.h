
#ifndef ASS3C_ENCODERDECODERCLIENT_H
#define ASS3C_ENCODERDECODERCLIENT_H
#include <string>
#include <vector>
#include <unordered_map>
#include <iostream>
using namespace std;

class EncoderDecoderClient {
private:
    string serverMessage;
    unordered_map<string,short> opcodeMap;

public:
    string decodeOpcodeBytes(char *bytes);
    string decodeAckMsgBytes(const char byte);
    string encode(string &message);
    short bytesToShort(const char *bytesArr);
    void shortToBytes(short num, string &bytes);
    EncoderDecoderClient();
    void cleanServerMsg();
    void pushWord(const string &word, string &bytes);
};


#endif
