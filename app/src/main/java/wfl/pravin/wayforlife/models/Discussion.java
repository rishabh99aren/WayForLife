package wfl.pravin.wayforlife.models;

public class Discussion {
    private String title;
    private String userId;
    private String userName;
    private String key;

    public Discussion() {
    }

    public Discussion(String title, String userId, String userName, String key) {
        this.title = title;
        this.userId = userId;
        this.userName = userName;
        this.key = key;
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

    @Override
    public String toString() {
        return "Discussion{" +
                "title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", key=" + key +
                '}';
    }
}
