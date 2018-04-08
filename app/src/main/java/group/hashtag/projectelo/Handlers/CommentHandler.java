package group.hashtag.projectelo.Handlers;

/**
 * Created by amant on 21-03-2018.
 */

public class CommentHandler {
    String commentId;
    String commentText;
    String commentUserId;
    String commentUserName;


    /**
     * Default constructor
     */
    public CommentHandler() {

    }

    /**
     * Parameterized Constructor
     * @param commentId
     * @param commentText
     * @param commentUserId
     * @param commentUserName
     */
    public CommentHandler(String commentId, String commentText, String commentUserId, String commentUserName) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.commentUserId = commentUserId;
        this.commentUserName = commentUserName;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

}
