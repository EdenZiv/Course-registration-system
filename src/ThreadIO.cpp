#include "../include/ThreadIO.h"

using std::string;
using std::cout;

ThreadIO::ThreadIO(ConnectionHandler &coHand): connectionH(coHand) {}

void ThreadIO::run() {
    const short bufsize1 = 1024;
    char buf[bufsize1];
    while (!connectionH.terminate) {
        cin.getline(buf, bufsize1);
        string line(buf);
        string encodedString = connectionH.encodeDecode.encode(line);
        const char* toSend= &encodedString[0];
        if (!connectionH.sendBytes(toSend,encodedString.size())) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
    }
}
