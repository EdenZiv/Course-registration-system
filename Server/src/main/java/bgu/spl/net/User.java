package bgu.spl.net;

public class User {
    private String userName;
    private String password;
    private String type;
    private boolean loggedIn;

    public User(String userName, String password, String type){
        this.userName=userName;
        this.password=password;
        this.type=type;
        loggedIn=false;
    }

    public boolean IsLoggedIn(){
        return loggedIn;
    }

    public String getPassword(){
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getType() {
        return type;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
