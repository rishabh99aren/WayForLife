package wfl.pravin.wayforlife;

import android.location.LocationListener;
import android.view.LayoutInflater;

public class Upload
{
    private String Title;
    private String UserReport;
    private String CityName;
    private Double Lattitude;
    private Double Longitude;
    private String UserId;
    private String UserName;
    private String Timestamp;
    private String ImageUrl;
//    private String mTitle;
    public Upload()
    {

    }
    public Upload(String Discription, String imageUrI,String title,String cityName,
                  Double lattitude,Double longitude,String userId,String userName,String timestamp)
    {
        if(Discription.trim().equals(""))
        {
            Discription = "No Name";
        }

        UserReport = Discription;
        ImageUrl = imageUrI;
        Title = title;
        CityName = cityName;
        Lattitude=lattitude;
        Longitude = longitude;
        UserId = userId;
        UserName=userName;
        Timestamp =timestamp;
    }

    public String getName()
    {
        return UserReport;
    }
    public void setUserReport(String Discription)
    {
         UserReport = Discription;
    }
    public void getImageUrl(String imageUrI)
    {
        ImageUrl = imageUrI;
    }

    public String getTitle()
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


    public Double getLattitude()
    {
        return Lattitude;
    }

    public void setLattitude(Double lattitude)
    {
        Lattitude= lattitude;
    }

    public Double getLongitude()
    {
        return Longitude;
    }

    public void setLongitude(Double longitude)
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
    }

}