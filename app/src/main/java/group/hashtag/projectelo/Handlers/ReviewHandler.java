package group.hashtag.projectelo.Handlers;

/**
 * Created by nikhilkamath on 10/03/18.
 */

public class ReviewHandler {

    public String Comment;
    public String Device_Category;
    public String Like;
    public String RevID;
    public String ReviewTitle;
    public String UserId;
    public String Device;
    public String LikeNumber;


    public ReviewHandler() {
    }

    public ReviewHandler(String comment, String device_Category, String like, String revID, String reviewTitle, String userId, String device, String likeNumber) {
        Comment = comment;
        Device_Category = device_Category;
        Like = like;
        RevID = revID;
        ReviewTitle = reviewTitle;
        UserId = userId;
        Device = device;
        LikeNumber = likeNumber;
    }
}
