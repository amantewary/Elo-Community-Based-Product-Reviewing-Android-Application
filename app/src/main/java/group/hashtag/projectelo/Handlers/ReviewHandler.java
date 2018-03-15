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
    public String Dislike;



    public ReviewHandler() {
    }

    private ReviewHandler(String Comment, String Device_Category, String Like, String RevID, String ReviewTitle, String UserId, String Device, String Dislike) {
        this.Comment = Comment;
        this.Device_Category = Device_Category;
        this.Like = Like;
        this.RevID = RevID;
        this.ReviewTitle = ReviewTitle;
        this.UserId = UserId;
        this.Device = Device;
        this.Dislike = Dislike;
    }

}
