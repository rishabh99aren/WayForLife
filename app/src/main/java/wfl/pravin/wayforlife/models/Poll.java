package wfl.pravin.wayforlife.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Poll {
    private String title;
    private String userId;
    private String userName;
    private String key;
    private List<String> options;

    // these properties are not mapped in firebase
    @Exclude
    private boolean voted = false;
    @Exclude
    private long votedOption = -1;

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public long getVotedOption() {
        return votedOption;
    }

    public void setVotedOption(long votedOption) {
        this.votedOption = votedOption;
    }

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
