package group.hashtag.projectelo.Handlers;

/**
 * Created by amant on 19-03-2018.
 */

public class NewReviewHandler {

    String userId;
    String reviewId;
    String reviewTitle;
    String reviewDescription;
    String device;
    String category;
    String reviewImage;

    /**
     * Default constructor
     */
    public NewReviewHandler() {

    }

    /**
     * Parameterized Constructor
     * @param userId
     * @param reviewId
     * @param reviewTitle
     * @param reviewDescription
     * @param device
     * @param category
     */
    public NewReviewHandler(String userId, String reviewId, String reviewTitle, String reviewDescription, String device, String category) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewDescription = reviewDescription;
        this.device = device;
        this.category = category;
    }

    /**
     * Parameterized Constructor
     * @param userId
     * @param reviewId
     * @param reviewTitle
     * @param reviewDescription
     * @param device
     * @param category
     * @param reviewurl
     */
    public NewReviewHandler(String userId, String reviewId, String reviewTitle, String reviewDescription, String device, String category, String reviewurl) {
        this.userId = userId;
        this.reviewId = reviewId;
        this.reviewTitle = reviewTitle;
        this.reviewDescription = reviewDescription;
        this.device = device;
        this.category = category;
        this.reviewImage = reviewurl;
    }

    public String getUserId() {
        return userId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public String getDevice() {
        return device;
    }

    public String getCategory() {
        return category;
    }
}
