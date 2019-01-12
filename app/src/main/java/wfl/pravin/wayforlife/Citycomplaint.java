package wfl.pravin.wayforlife;

public class Citycomplaint {
    public String userid;
    public String username;
    public String complainttitle;
    public String complaint;
    public String city;
    public String timestamp;
    public String image;
    public String latitude;
    public String longitude;

    public Citycomplaint() {
    }


    public Citycomplaint(String userid, String username, String complainttitle,
                         String complaint, String city, String timestamp,String image,String latitude,String longitude) {
        this.userid = userid;
        this.username = username;
        this.complainttitle=complainttitle;
        this.complaint = complaint;
        this.city = city;
        this.timestamp = timestamp;
        this.image=image;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getUserid() {
        return userid;
    }

    public String getComplainttitle() {
        return complainttitle;
    }

    public void setComplainttitle(String complainttitle) {
        this.complainttitle = complainttitle;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
