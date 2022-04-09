package bgu.spl.net.MsgOperations;

import bgu.spl.net.User;

public class Logout {
    public Logout(){}

    public String act(User user){
        if(!user.IsLoggedIn())
            return "ERROR 4";
        else{
            user.setLoggedIn(false);
            return "ACK 4";
        }

    }
}
