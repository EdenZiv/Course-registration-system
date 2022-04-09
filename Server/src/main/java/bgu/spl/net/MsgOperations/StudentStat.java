package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentStat {
    public StudentStat(){}

    public String act(User user, String studentUserName){
        Database database=Database.getInstance();
        if(user.getType().equals("Admin") && user.IsLoggedIn() && database.isRegister(studentUserName)) {
            ArrayList<String> coursesArray=database.getStudentCoursesList(studentUserName);
            String courses;
            if(coursesArray.size()==0)
                courses="[]";
            else
                courses=coursesArray.toString().replace(" ","");
            return "ACK 8" + "\nStudent: " + studentUserName + "\nCourses: " + courses;
        }
        return "ERROR 8";
    }
}
