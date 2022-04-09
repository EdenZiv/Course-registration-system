package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CourseStat {
    public CourseStat(){}

    public String act(User user, String courseNum){
        Database database=Database.getInstance();
        if(user.getType().equals("Admin") && user.IsLoggedIn() && database.isExistedCourse(courseNum)) {
            HashMap<String, Object> courseInfo = database.getCourseInfo(courseNum);
            String courseName=(String)courseInfo.get("courseName");
            String seatsAvailable=database.getAvailableCourseSeats(courseNum).toString();
            String maxSeats=database.getMaxNumOfStudents(courseNum).toString();
            ArrayList<String> studentsRegistered=database.getCourseStudentsList(courseNum);
            String studentsReg;
            if(studentsRegistered==null)
                studentsReg="[]";
            else {
                studentsRegistered.sort(String::compareTo);
                studentsReg = studentsRegistered.toString().replace(" ","");
            }
            return "ACK 7" + "\nCourse: (" + courseNum + ") " + courseName + "\n"
                    + "Seats Available: " + seatsAvailable + "/" + maxSeats + "\n"
                    + "Students Registered: " +studentsReg ;
            }
        return "ERROR 7";
    }
}
