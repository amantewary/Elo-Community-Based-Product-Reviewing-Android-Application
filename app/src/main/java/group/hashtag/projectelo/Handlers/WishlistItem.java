package group.hashtag.projectelo.Handlers;

/**
 * Created by amant on 16-03-2018.
 */

public class WishlistItem {
    private String deviceId;
    private String deviceName;
    private String deviceCategory;

    public WishlistItem() {

    }

    public WishlistItem(String DeviceId, String DeviceName, String DeviceCategory) {
        this.deviceId = DeviceId;
        this.deviceName = DeviceName;
        this.deviceCategory = DeviceCategory;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceCategory() {
        return deviceCategory;
    }
}
