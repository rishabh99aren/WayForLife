package wfl.pravin.wayforlife;

public class Upload
{
    private String Title;
    private String UserReport;
    private String CityName;
    private String mImageUrI;
//    private String mTitle;
    public Upload()
    {

    }
    public Upload(String Discription, String imageUrI,String title,String cityName)
    {
        if(Discription.trim().equals(""))
        {
            Discription = "No Name";
        }

        UserReport = Discription;
        mImageUrI = imageUrI;
        Title = title;
        CityName = cityName;
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
        mImageUrI = imageUrI;
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

}