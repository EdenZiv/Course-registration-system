package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

import java.util.Arrays;

public class KdamCheck {
    public KdamCheck(){}

    public String act(User user, String courseNum){
        Database database=Database.getInstance();
        if(user.getType().equals("Student") && user.IsLoggedIn() && database.isExistedCourse(courseNum)) {
            String kdamString =database.getKdamCourses(courseNum);
            return "ACK 6" +"\n"+kdamString;
        }
        return "ERROR 6";
    }
}
