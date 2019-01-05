package wfl.pravin.wayforlife;

public class Upload
{
    private String mName;
    private String mImageUrI;

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


}