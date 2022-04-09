package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

public class Login {

    public Login(){
    }

    public String act(User user,String password){
        if(Database.getInstance().loginUser(user,password))
            return "ACK 3";
        else
            return "ERROR 3";
    }
}
