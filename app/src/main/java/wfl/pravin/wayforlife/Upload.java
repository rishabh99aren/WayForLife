package wfl.pravin.wayforlife;

public class Upload
{
    private String Title;
    private String desc;
    private String City;
    private double lat;
    private double lng;
    private String UserId;
    private String UserName;
    private String Timestamp;
    private String ImageUrl;

    public Upload()
    {

    }

    public Upload(String Discription, String title, String cityName, String imageUrl,
                  double lat, double lng, String userId, String userName, String timestamp)
    {
        if(Discription.trim().equals(""))
        {
            Discription = "No Name";
        }

        this.desc = Discription;
        this.ImageUrl = imageUrl;
        this.Title = title;
        this.City = cityName;
        this.lat = lat;
        this.lng = lng;
        this.UserId = userId;
        this.UserName=userName;
        this.Timestamp =timestamp;
    }

    public String getName()
    {
        return desc;
    }

    public void setDesc(String Discription)
    {
        desc = Discription;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return desc;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

}