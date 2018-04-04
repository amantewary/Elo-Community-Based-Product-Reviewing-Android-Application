package group.hashtag.projectelo.Handlers;

/**
 * Created by nikhilkamath on 10/03/18.
 */

public class ReviewHandler {


   public String category;
   public String device;
   public String reviewDescription;
   public String reviewId;
   public String reviewTitle;
   public String userId;
   public String reviewImage;

    public ReviewHandler() {
    }

    public ReviewHandler(String category, String device, String reviewDescription, String reviewId, String reviewTitle, String userId, String reviewImage) {
        this.category = category;
        this.device = device;
        this.reviewDescription = reviewDescription;
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.userId = userId;
        this.reviewImage = reviewImage;
    }

    public String getDevices() {
        return device;
    }

    public void setDevices(String device) {
        this.device = device;
    }

    public String getCategorys() {
        return category;
    }
}
