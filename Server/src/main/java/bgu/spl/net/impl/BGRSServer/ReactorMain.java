package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.BGRSEncoderDecoder;
import bgu.spl.net.BGRSmessageProtocol;
import bgu.spl.net.Database;
import bgu.spl.net.srv.Server;
import bgu.spl.net.impl.*;

public class ReactorMain {

    public static void main(String[] args) {
        int port;
        int numOfThreads;
        /*if (args.length<2)
            throw new IllegalArgumentException("Not valid input");

         */
        try{
            port=Integer.parseInt(args[0]);
            numOfThreads=Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
        Database database=Database.getInstance();
        database.initialize("Courses.txt");
        Server.reactor(numOfThreads,port, BGRSmessageProtocol::new, BGRSEncoderDecoder::new).serve();
    }
}
