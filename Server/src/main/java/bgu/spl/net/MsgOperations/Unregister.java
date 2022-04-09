package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

public class Unregister {
    public Unregister(){}

    public String act(User user, String courseNum){
        Database database=Database.getInstance();
        if(user.getType().equals("Student") && user.IsLoggedIn() && database.isExistedCourse(courseNum)
                && database.isStudentRegisteredToCourse(courseNum,user.getUserName())) {
                database.studentCourseUnregister(courseNum,user.getUserName());
                return "ACK 10";
        }
        return "ERROR 10";
    }
}
