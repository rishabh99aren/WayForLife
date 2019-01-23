package wfl.pravin.wayforlife;

public class Upload
{
    private String mName;
    private String mImageUrI;
//    private String mTitle;
    public Upload()
    {

    }
    public Upload(String Discription, String imageUrI)
    {
        if(Discription.trim().equals(""))
        {
            Discription = "No Name";
        }

        mName = Discription;
        mImageUrI = imageUrI;
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

    //  public String getmTitle()
    //{
    //  return mTitle;
    //}
    //public void setmTitle(String title)
    // {
    //   mTitle = title;
    //}

}