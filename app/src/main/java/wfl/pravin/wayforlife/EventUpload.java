package wfl.pravin.wayforlife;

public class EventUpload
{

    private String Title;
    private String UserReport;
  //  private String Date1;
   private String Timestamp;

   //    private String mTitle;
    public EventUpload()

    {

    }
    public EventUpload(String Discription,String title,String timestamp)
    {
        if(Discription.trim().equals(""))
        {
            Discription = "No Name";
        }

        UserReport = Discription;
//        ImageUrl = imageUrI;
        Title = title;
      //  CityName = cityName;
        Timestamp =timestamp;
       // Date1 = date1;
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

    public String getTitle()
    {
        return Title;
    }
    public void setTitle(String title)
    {
        Title = title;

    }
//
//    public String getCity()
//    {
//        return CityName;
//    }
//
//    public void setmCityName(String cityName)
//    {
//        CityName = cityName;
//    }

    public String getTimestamp()
    {
        return Timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        Timestamp = timestamp;
    }

//    public String getDate()
//    {
//        return Date1;
//    }
//
//    public void setDate1(String date1)
//    {
//        Date1=date1;
//    }

}
