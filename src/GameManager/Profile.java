package GameManager;

public class Profile implements java.io.Serializable {
    private static final long serialVersionUID = 1L;  // Versión de serialización
    private int age;
    private String email;
    private String country;

    public Profile (String email, String country, int age) {
        this.age = age;
        this.email = email;
        this.country = country;
    }
    public int getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
    public String getCountry() {
        return country;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}
