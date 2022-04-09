package bgu.spl.net;

import bgu.spl.net.MsgOperations.*;
import bgu.spl.net.api.MessagingProtocol;

public class BGRSmessageProtocol implements MessagingProtocol<String> {
    private boolean shouldTerminate = false;
    private User currentUser=null;

    @Override
    public String process(String msg) {
        String[] message = msg.split(" ");
        int opcode = Integer.decode(message[0]);
        switch (opcode) {
            case 1: //Admin register
                if (currentUser==null && !Database.getInstance().isRegister(message[1])) {
                    AdminReg admin = new AdminReg();
                    return admin.act(new User(message[1], message[2], "Admin"));
                }
                return "ERROR 1";
            case 2: //Student registerA
                if (currentUser==null && !Database.getInstance().isRegister(message[1])) {
                    StudentReg student = new StudentReg();
                    return student.act(new User(message[1], message[2], "Student"));
                }
                return "ERROR 2";
            case 3: //User Login
                if (currentUser==null && Database.getInstance().isRegister(message[1])) {
                    currentUser=Database.getInstance().getUser(message[1]);
                    Login login=new Login();
                    return login.act(currentUser,message[2]);
                }
                return "ERROR 3";
            case 4: //User Logout
                if (currentUser!=null) {
                    Logout logout = new Logout();
                    String response=logout.act(currentUser);
                    shouldTerminate=response.equals("ACK 4");
                    return response;
                }
                return "ERROR 4";
            case 5: //Student course registration
                if (currentUser!=null){
                    CourseReg courseReg=new CourseReg();
                    return courseReg.act(currentUser,message[1]);
                }
                return "ERROR 5";
            case 6: //Kdam check
                if (currentUser!=null) {
                    KdamCheck kdamCheck=new KdamCheck();
                    return kdamCheck.act(currentUser,message[1]);
                }
                return "ERROR 6";
            case 7: //course status
                if (currentUser!=null){
                    CourseStat courseStat=new CourseStat();
                    return courseStat.act(currentUser,message[1]);
                }
                return "ERROR 7";
            case 8: //Student status
                if (currentUser!=null){
                    StudentStat studentStat=new StudentStat();
                    return studentStat.act(currentUser,message[1]);
                }
                return "ERROR 8";
            case 9: //is registered (student to course)
                if (currentUser!=null){
                    IsRegistered isRegistered=new IsRegistered();
                    return isRegistered.act(currentUser,message[1]);
                }
                return "ERROR 9";
            case 10: //unregister student from course
                if (currentUser!=null){
                    Unregister unRegistered=new Unregister();
                    return unRegistered.act(currentUser,message[1]);
                }
                return "ERROR 10";
            case 11: //my courses message
                if (currentUser!=null){
                    MyCourses myCourses=new MyCourses();
                    return myCourses.act(currentUser);
                }
                return "ERROR 11";
        }
        return null;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
