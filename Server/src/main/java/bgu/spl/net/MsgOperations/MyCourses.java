package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

public class MyCourses {
    public MyCourses(){}

    public String act(User user){
        Database database=Database.getInstance();
        if(user.getType().equals("Student") && user.IsLoggedIn()) {
            String studentCourses=database.getStudentCoursesList(user.getUserName()).toString().replace(" ","");
            return "ACK 11" + "\n"+studentCourses;
        }
        return "ERROR 11";
    }
}
