package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

public class IsRegistered {
    public IsRegistered(){}

    public String act(User user, String courseNum){
        Database database=Database.getInstance();
        if(user.getType().equals("Student") && user.IsLoggedIn() && database.isExistedCourse(courseNum)) {
            if (database.isStudentRegisteredToCourse(courseNum,user.getUserName()))
                return "ACK 9\nREGISTERED";
            else
                return "ACK 9\nNOT REGISTERED";
        }
        return "ERROR 9";
    }
}
