package GameManager;

import java.io.Serializable;

public class User implements Observer, Serializable {
    private static final long serialVersionUID = 1L;  

    private String userName;
    private Profile profile;
    private String password;

    public User() {
        this.userName = "";
        this.password = "";
        this.profile = new Profile("", "", 0); 
    }

    public User(String userName, String password, Profile profile) {
        this.userName = userName;
        this.password = password;
        this.profile = profile;
    }

    @Override
    public void update(Player result) {
        System.out.println("User " + userName + " has been notified.");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
