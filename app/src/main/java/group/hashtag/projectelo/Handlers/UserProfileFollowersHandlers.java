package group.hashtag.projectelo.Handlers;

/**
 * Created by nikhilkamath on 21/03/18.
 */

public class UserProfileFollowersHandlers {

    public String followerNameUserProfile;
    public String followerFollowedDate;

    /**
     * Default constructor
     */
    public UserProfileFollowersHandlers() {
    }

    /**
     * Parameterized Constructor
     * @param followerNameUserProfile
     * @param followerFollowedDate
     */
    public UserProfileFollowersHandlers(String followerNameUserProfile, String followerFollowedDate) {
        this.followerNameUserProfile = followerNameUserProfile;
        this.followerFollowedDate = followerFollowedDate;
    }
}
