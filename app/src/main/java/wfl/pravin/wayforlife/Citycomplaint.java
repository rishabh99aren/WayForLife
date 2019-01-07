package wfl.pravin.wayforlife;

public class Citycomplaint {
    public String userid;
    public String username;
    public String complaint;
    public String city;
    public String timestamp;

    public Citycomplaint() {
    }

    public Citycomplaint(String username) {
        this.username = username;
    }

    public Citycomplaint(String userid, String username, String complaint, String city, String timestamp) {
        this.userid = userid;
        this.username = username;
        this.complaint = complaint;
        this.city = city;
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
