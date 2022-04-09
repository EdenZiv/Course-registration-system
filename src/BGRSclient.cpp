#include <cstdlib>
#include "../include/connectionHandler.h"
#include "../include/ThreadIO.h"
#include "thread"

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);
    bool terminateProgram=false;
    ConnectionHandler connectionHandler(host, port, terminateProgram);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    ThreadIO threadIO(connectionHandler);
    thread ioThread(&ThreadIO::run, &threadIO);

    while (!connectionHandler.terminate) {
        const short bufsize = 4;
        char serverBytes[bufsize];
        if (!connectionHandler.getBytes(serverBytes, 4)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        string decodedMsg = connectionHandler.encodeDecode.decodeOpcodeBytes(serverBytes);
        char ackByte[1];
        if (!connectionHandler.getBytes(ackByte, 1)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        decodedMsg = connectionHandler.encodeDecode.decodeAckMsgBytes(ackByte[0]);
        while (decodedMsg.empty()) {
            if (!connectionHandler.getBytes(ackByte, 1)) {
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            decodedMsg = connectionHandler.encodeDecode.decodeAckMsgBytes(ackByte[0]);
        }
        cout << decodedMsg << endl;
        connectionHandler.encodeDecode.cleanServerMsg();
        connectionHandler.terminate = (decodedMsg == "ACK 4");
    }

    ioThread.detach();
    return 0;
}
