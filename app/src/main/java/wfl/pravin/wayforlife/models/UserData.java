package wfl.pravin.wayforlife.models;

public class UserData {
    private String name;
    private String uid;
    private String state;
    private String city;
    private String email;

    public UserData() {

    }

    public UserData(String name, String uid, String state, String city, String email) {
        this.name = name;
        this.uid = uid;
        this.state = state;
        this.city = city;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
