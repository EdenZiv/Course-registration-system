package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

public class AdminReg {

    public AdminReg(){}

    public String act(User adminUser){
        if(Database.getInstance().addRegisteredUser(adminUser))
            return "ACK 1";
        else
            return "ERROR 1";
    }
}
