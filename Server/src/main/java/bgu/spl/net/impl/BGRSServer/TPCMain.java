package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.BGRSEncoderDecoder;
import bgu.spl.net.BGRSmessageProtocol;
import bgu.spl.net.Database;
import bgu.spl.net.srv.Server;
import bgu.spl.net.impl.*;

public class TPCMain {

    public static void main(String[] args) {
        int port;
        /*if (args.length<1)
            throw new IllegalArgumentException("Not valid input");

         */
        try{
            port=Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
        Database database=Database.getInstance();
        database.initialize("Courses.txt");
        Server.threadPerClient(port, BGRSmessageProtocol::new, BGRSEncoderDecoder::new).serve();
    }
}
