package wfl.pravin.wayforlife;

public class Events {

    public String title;
    public String description;
    public String date;
    public String address;
    public String state;
    public String city;
    public String time;

    public Events() {
    }


    public Events(String title, String description, String date, String address, String state, String city, String time) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.address = address;
        this.state = state;
        this.city = city;
        this.time = time;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
