#ifndef ASS3C_THREADIO_H
#define ASS3C_THREADIO_H

#include "connectionHandler.h"

class ThreadIO {
private:
    ConnectionHandler & connectionH;
public:
    ThreadIO(ConnectionHandler &coHand);
    void run();
};


#endif
