package group.hashtag.projectelo.Handlers;

/**
 * Created by nikhilkamath on 18/02/18.
 */

public class UserHandler {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;
    public String UserId;
    public String DisplayPic;

    public String getDisplayPicss() {
        return DisplayPic;
    }

    public void setDisplayPicss(String displayPic) {
        DisplayPic = displayPic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UserHandler() {
    }

    public UserHandler(String name, String userId, String email) {
        this.name = name;
        UserId = userId;
        this.email = email;
    }

    public UserHandler(String name, String email, String UserId, String DisplayPic) {
        this.name = name;
        this.email = email;
        this.UserId = UserId;
        this.DisplayPic = DisplayPic;
    }

}
