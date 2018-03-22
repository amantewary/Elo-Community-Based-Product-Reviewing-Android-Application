package group.hashtag.projectelo.Handlers;

/**
 * Created by nikhilkamath on 21/03/18.
 */

public class NewUserDevice {
    public String category;
    public String deviceName;
    public String userId;
    public String deviceId;

    public NewUserDevice() {
    }

    public NewUserDevice(String category, String deviceName, String userId, String deviceId) {
        this.category = category;
        this.deviceName = deviceName;
        this.userId = userId;
        this.deviceId = deviceId;
    }


}
