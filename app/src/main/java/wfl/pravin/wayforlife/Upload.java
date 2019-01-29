package wfl.pravin.wayforlife;

public class Upload
{
    private String mTitle;
    private String mName;
    private String mCityName;
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

        mName = Discription;
        mImageUrI = imageUrI;
        mTitle = title;
        mCityName = cityName;
    }

    public String getName()
    {
        return mName;
    }
    public void setmName(String Discription)
    {
         mName = Discription;
    }
    public void getImageUrl(String imageUrI)
    {
        mImageUrI = imageUrI;
    }

    public String getTitle()
    {
      return mTitle;
    }
    public void setmTitle(String title)
    {
       mTitle = title;
       
    }

    public String getCity()
    {
        return mCityName;
    }

    public void setmCityName(String cityName)
    {
        mCityName = cityName;
    }




}