# All Targets
CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -pthread

all: bin/BGRSclient 

bin/BGRSclient: bin/BGRSclient.o bin/connectionHandler.o bin/EncoderDecoderClient.o bin/echoClient.o bin/ThreadIO.o
	@echo 'Building target: main'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/BGRSclient  bin/BGRSclient.o bin/connectionHandler.o bin/EncoderDecoderClient.o bin/echoClient.o bin/ThreadIO.o $(LDFLAGS)
	@echo 'Finished building target: main'
	@echo ' '

#bin/main.o: src/main.cpp
#	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/main.o src/main.cpp

bin/BGRSclient.o: src/BGRSclient.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/BGRSclient.o src/BGRSclient.cpp

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/connectionHandler.o src/connectionHandler.cpp

bin/EncoderDecoderClient.o: src/EncoderDecoderClient.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/EncoderDecoderClient.o src/EncoderDecoderClient.cpp

bin/echoClient.o: src/echoClient.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/echoClient.o src/echoClient.cpp

bin/ThreadIO.o: src/ThreadIO.cpp
	g++ -g -Wall -Weffc++ -std=c++11  -c -Iinclude -o bin/ThreadIO.o src/ThreadIO.cpp

clean:
	rm -f bin/*
