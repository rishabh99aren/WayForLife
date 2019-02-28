package wfl.pravin.wayforlife;

public class Loginclass {

    public String Username;
    public String Userrid;
    public String City;
    public String State;
    public String Email;

    public Loginclass(String username, String userrid, String city,String state,String email ) {

        Username = username;
        Userrid = userrid;
        City = city;
        State=state;
        Email=email;
    }

    public Loginclass() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserrid() {
        return Userrid;
    }

    public void setUserrid(String userrid) {
        Userrid = userrid;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
