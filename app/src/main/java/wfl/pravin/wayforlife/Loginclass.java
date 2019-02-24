package wfl.pravin.wayforlife;

public class Loginclass {

    public String Username;
    public String Userrid;
    public String City;

    public Loginclass(String username, String userrid, String city) {
        Username = username;
        Userrid = userrid;
        City = city;
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
}
