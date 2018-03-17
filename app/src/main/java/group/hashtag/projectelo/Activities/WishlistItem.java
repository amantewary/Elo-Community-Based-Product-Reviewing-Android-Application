package group.hashtag.projectelo.Activities;

/**
 * Created by amant on 16-03-2018.
 */

public class WishlistItem {
    private String DeviceId;
    private String DeviceName;
    private String DeviceCategory;

    public WishlistItem() {

    }

    public WishlistItem(String DeviceId, String DeviceName, String DeviceCategory) {
        this.DeviceId = DeviceId;
        this.DeviceName = DeviceName;
        this.DeviceCategory = DeviceCategory;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public String getDeviceCategory() {
        return DeviceCategory;
    }
}
