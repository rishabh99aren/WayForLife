package wfl.pravin.wayforlife;

import android.content.Context;
import android.content.SharedPreferences;

import wfl.pravin.wayforlife.models.UserData;

import static android.content.Context.MODE_PRIVATE;

public class UserDataUtils {
    static void setClientData(UserData user) {
        ClientData.setName(user.getName());
        ClientData.setUid(user.getUid());
        ClientData.setCity(user.getCity());
        ClientData.setEmail(user.getEmail());
        ClientData.setState(user.getState());
    }

    static void saveDataInSharedPref(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        editor.putString("uid", ClientData.getUid());
        editor.putString("name", ClientData.getName());
        editor.putString("email", ClientData.getEmail());
        editor.putString("city", ClientData.getCity());
        editor.putString("state", ClientData.getState());

        editor.commit();
    }

    private static void setDataFromSharedPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        ClientData.setUid(pref.getString("uid", null));
        ClientData.setName(pref.getString("name", null));
        ClientData.setEmail(pref.getString("email", null));
        ClientData.setCity(pref.getString("city", null));
        ClientData.setState(pref.getString("state", null));
    }

    static void clearSharedPref(Context context) {
        ClientData.clearAllData();
        SharedPreferences.Editor editor = context.getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    static void refreshUserData(Context context) {
        if (ClientData.getUid() == null) {
            setDataFromSharedPref(context);
        }
    }
}
