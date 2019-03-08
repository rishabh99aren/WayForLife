package wfl.pravin.wayforlife;

public class ClientData {
    private static String name;
    private static String email;
    private static String uid;
    private static String city;
    private static String state;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ClientData.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        ClientData.email = email;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        ClientData.uid = uid;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        ClientData.city = city;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        ClientData.state = state;
    }

    public static void clearAllData() {
        name = null;
        email = null;
        uid = null;
        city = null;
        state = null;
    }
}
