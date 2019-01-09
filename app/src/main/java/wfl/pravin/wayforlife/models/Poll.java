package wfl.pravin.wayforlife.models;

import java.util.List;

public class Poll {
    private String title;
    private String userId;
    private String userName;
    private String key;
    private List<String> options;

    public Poll() {
    }

    public Poll(String title, String userId, String userName, String key, List<String> options) {
        this.title = title;
        this.userId = userId;
        this.userName = userName;
        this.key = key;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
