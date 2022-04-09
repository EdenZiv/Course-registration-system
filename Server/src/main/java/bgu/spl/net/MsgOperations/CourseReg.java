package bgu.spl.net.MsgOperations;

import bgu.spl.net.Database;
import bgu.spl.net.User;

public class CourseReg {
    public CourseReg(){}

    public String act(User user, String courseNum){
        Database database=Database.getInstance();
        if(user.getType().equals("Student") && user.IsLoggedIn() && database.isExistedCourse(courseNum)
                && database.getAvailableCourseSeats(courseNum)>0
                && !database.isStudentRegisteredToCourse(courseNum,user.getUserName())) {
            String kdam=database.getKdamCourses(courseNum);
            String[] kdamCourses=kdam.substring(1,kdam.length()-1).split(",");
            if (!kdamCourses[0].equals(""))
                for (String course : kdamCourses) {
                    if (!database.hasCompletedCourse(course, user.getUserName()))
                        return "ERROR 5";
                }
            database.studentCourseRegister(courseNum, user.getUserName());
            return "ACK 5";
        }
        return "ERROR 5";
    }
}
