package wfl.pravin.wayforlife.models;

public class Vote {
    private String userId;
    private int option;

    public Vote(String userId, int option) {
        this.userId = userId;
        this.option = option;
    }

    public Vote() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}
