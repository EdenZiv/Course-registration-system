package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

public class StudentReg {

    public StudentReg(){}

    public String act(User studentUser){
        if(Database.getInstance().addRegisteredUser(studentUser))
            return "ACK 2";
        else
            return "ERROR 2";
    }
}
