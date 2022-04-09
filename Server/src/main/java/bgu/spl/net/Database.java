package bgu.spl.net;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	private final ArrayList<User> registeredUsers;
	private final LinkedHashMap<String ,HashMap<String,Object>> dataBaseMap; //inserts and iterates the elements by order
	private final LinkedHashMap<String, ArrayList<String>> coursesStudentMap; // contain list of courses and the student that register them.
	private final ConcurrentHashMap<String, ArrayList<String>> studentCompletedCourses; // contain list of courses that the student has completed.

	private static class DatBbaseSingeltonHolder{  //singelton implementation
		private static final Database instance= new Database();
	}

	//to prevent user from creating new Database
	private Database() {
		dataBaseMap= new LinkedHashMap<>();
		coursesStudentMap= new LinkedHashMap<>();
		registeredUsers =new ArrayList<>();
		studentCompletedCourses=new ConcurrentHashMap<>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() { //instance of the singelton object
			return DatBbaseSingeltonHolder.instance;
		}
	
	/**
	 * loades the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	public boolean initialize(String coursesFilePath) {
		try {
			File myFile= new File(coursesFilePath);
			Scanner myReader= new Scanner(myFile);
			while (myReader.hasNextLine()){
				String dataLine=myReader.nextLine();
				setDataBaseMap(dataLine);
			}
			return true;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setDataBaseMap(String dataLine){
		String[] courseFeatures=dataLine.split("\\|");
		String courseNum=courseFeatures[0];
		dataBaseMap.put(courseNum,new HashMap<>());
		coursesStudentMap.put(courseNum,new ArrayList<>()); //add the course to courseStudent map
		dataBaseMap.get(courseNum).put("courseName",courseFeatures[1]);
		String kdamCourses=courseFeatures[2];
		dataBaseMap.get(courseNum).put("kdamCoursesList",kdamCourses);
		Integer maxStudents=Integer.decode(courseFeatures[3]);
		dataBaseMap.get(courseNum).put("numOfMaxStudent",maxStudents);
	}

	public synchronized boolean addRegisteredUser(User u){
		if(!isRegister(u.getUserName())) {
			registeredUsers.add(u);
			return true;
		}
		return false;
	}

	public synchronized boolean loginUser(User user,String password){
		if(user.IsLoggedIn() || !user.getPassword().equals(password))
			return false;
		user.setLoggedIn(true);
		return true;
	}

	public synchronized boolean isRegister(String userName){
		for (int i = 0; i < registeredUsers.size(); i++)
			if (registeredUsers.get(i).getUserName().equals(userName))
				return true;
		return false;
	}

	public String getKdamCourses(String courseNum){
		return (String)dataBaseMap.get(courseNum).get("kdamCoursesList");
	}

	public boolean isExistedCourse(String courseNum){
		return dataBaseMap.containsKey(courseNum);
	}

	public synchronized void studentCourseRegister(String courseNum, String username){
		coursesStudentMap.get(courseNum).add(username);
		if (studentCompletedCourses.containsKey(username))
			studentCompletedCourses.get(username).add(courseNum);
		else {
			studentCompletedCourses.put(username, new ArrayList<>());
			studentCompletedCourses.get(username).add(courseNum);
		}
	}

	public void studentCourseUnregister(String courseNum, String username){
		synchronized (coursesStudentMap) {
			coursesStudentMap.get(courseNum).remove(username);
		}
	}

	public Integer getMaxNumOfStudents(String courseNum){
		return (Integer)dataBaseMap.get(courseNum).get("numOfMaxStudent");
	}

	public boolean isStudentRegisteredToCourse(String courseNum, String userName){
		if(coursesStudentMap.get(courseNum)==null)
			return false;
		return coursesStudentMap.get(courseNum).contains(userName);
	}

	public Integer getAvailableCourseSeats(String courseNum){
		int studentRegNum;
		if(coursesStudentMap.get(courseNum)==null)
			studentRegNum=0;
		else
			studentRegNum=coursesStudentMap.get(courseNum).size();
		return ((Integer)dataBaseMap.get(courseNum).get("numOfMaxStudent"))-studentRegNum;
	}

	public HashMap<String,Object> getCourseInfo(String courseNum){
		return dataBaseMap.get(courseNum);
	}

	public ArrayList<String> getCourseStudentsList(String courseNum){
		synchronized (coursesStudentMap) {
			if (coursesStudentMap.get(courseNum) == null)
				return null;
			return coursesStudentMap.get(courseNum);
		}
	}

	public ArrayList<String> getStudentCoursesList(String userName){
		ArrayList<String> studentCoursesList=new ArrayList<>();
		synchronized (coursesStudentMap){
			coursesStudentMap.forEach((course,listOfStudents)->{
					if (listOfStudents.contains(userName))
						studentCoursesList.add(course);
		});}
		return studentCoursesList;
	}

	public boolean hasCompletedCourse(String courseNum, String userName) {
		if(studentCompletedCourses.get(userName)==null)
			return false;
		return studentCompletedCourses.get(userName).contains(courseNum);
	}

	public User getUser(String userName){
		if (isRegister(userName))
			for (User u : registeredUsers) {
				if (u.getUserName().equals(userName))
					return u;
			}
		return null;
	}
}

