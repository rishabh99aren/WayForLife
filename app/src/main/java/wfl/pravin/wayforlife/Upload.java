package wfl.pravin.wayforlife;

import android.location.LocationListener;
import android.view.LayoutInflater;

public class Upload
{
    private String Title;
    private String UserReport;
    private String City;
    private String Lattitude;
    private String Longitude;
    private String UserId;
    private String UserName;
    private String Timestamp;
    private String ImageUrl;
//    private String mTitle;
    public Upload()
    {

    }
    public Upload(String Discription,String title,String cityName,String imageUrI,
                  String lattitude,String  longitude,String userId,String userName,String timestamp)
    {
        if(Discription.trim().equals(""))
        {
            Discription = "No Name";
        }

        this.UserReport = Discription;
        this.ImageUrl = imageUrI;
        this.Title = title;
        this.City = cityName;
        this.Lattitude=lattitude;
        this.Longitude = longitude;
        this.UserId = userId;
        this.UserName=userName;
        this.Timestamp =timestamp;
    }

    public String getName()
    {
        return UserReport;
    }
    public void setUserReport(String Discription)
    {
         UserReport = Discription;
    }
//    public void getImageUrl(String imageUrI)
//    {
//        ImageUrl = imageUrI;
//    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUserReport() {
        return UserReport;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getLattitude() {
        return Lattitude;
    }

    public void setLattitude(String lattitude) {
        Lattitude = lattitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
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

    /*public String getTitle()
    {
      return Title;
    }
    public void setTitle(String title)
    {
       Title = title;

    }

    public String getCity()

    {
        return CityName;
    }

    public void setmCityName(String cityName)
    {
        CityName = cityName;
    }


    public String getLattitude()
    {
        return Lattitude;
    }

    public void setLattitude(String  lattitude)
    {
        Lattitude= lattitude;
    }

    public String getLongitude()
    {
        return Longitude;
    }

    public void setLongitude(String longitude)
    {
        Longitude=longitude;
    }

    public String getUserId()
    {
        return UserId;
    }

    public void setUserId(String userId)
    {
        UserId = userId;
    }

    public String getUserReport() {
        return UserReport;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUserName()
    {
        return UserName;
    }

    public void setUserName(String userName)
    {
        UserName = userName;
    }


    public String getTimestamp()
    {
        return Timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        Timestamp = timestamp;
    }*/

}