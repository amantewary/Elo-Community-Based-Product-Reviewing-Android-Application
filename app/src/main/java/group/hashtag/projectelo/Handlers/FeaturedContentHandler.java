package group.hashtag.projectelo.Handlers;

/**
 * Created by nikhilkamath on 12/03/18.
 */

public class FeaturedContentHandler {

    String Article_content;
    String Article_title;
    String Created_date;
    String External_User;
    String Feature_rev_1;

    public FeaturedContentHandler() {
    }

    public FeaturedContentHandler(String Article_content, String Article_title, String Created_date, String External_User, String Feature_rev_1) {
        this.Article_content = Article_content;
        this.Article_title = Article_title;
        this.Created_date = Created_date;
        this.External_User = External_User;
        this.Feature_rev_1 = Feature_rev_1;
    }

    public String getArticle_contentss() {
        return Article_content;
    }

    public void setArticle_contentss(String Article_content) {
        this.Article_content = Article_content;
    }

    public String getArticle_titless() {
        return Article_title;
    }

    public void setArticle_titless(String Article_title) {
        this.Article_title = Article_title;
    }

    public String getExternal_Userss() {
        return External_User;
    }

    public void setExternal_Userss(String external_User) {
        External_User = external_User;
    }
}
