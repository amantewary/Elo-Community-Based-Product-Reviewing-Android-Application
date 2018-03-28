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
    public String country;
    public String dob_date;
    public String dob_month;
    public String dob_year;
    public String webLink;
    public String gender;

    public String getCountry() {
        return country;
    }

    public String getDob_month() {
        return dob_month;
    }

    public String getDob_year() {
        return dob_year;
    }

    public String getWebLink() {
        return webLink;
    }

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

    public String getDob_date() {
        return dob_date;
    }

    public String getGender() {
        return gender;
    }

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

    public UserHandler(String name, String userId, String country, String dob_month, String dob_year, String webLink, String email, String gender, String dob_date) {
        this.name = name;
        UserId = userId;
        this.country = country;
        this.dob_date = dob_date;
        this.dob_month = dob_month;
        this.dob_year = dob_year;
        this.webLink = webLink;
        this.email = email;
        this.gender = gender;
    }
}
