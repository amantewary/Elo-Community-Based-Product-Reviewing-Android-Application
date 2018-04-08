package group.hashtag.projectelo.Activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import group.hashtag.projectelo.Handlers.CommentHandler;
import group.hashtag.projectelo.R;

/**
 * Created by amant on 21-03-2018.
 * Adapter class created to show all the comments posted by the user on any review
 */

public class CommentAdapter extends ArrayAdapter {
    Activity context;
    List<CommentHandler> commentList;


    public CommentAdapter(Activity context, List<CommentHandler> commentList) {
        super(context, R.layout.comment_list_item, commentList);
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View commentItem = inflater.inflate(R.layout.comment_list_item, null, true);


        TextView commentText = commentItem.findViewById(R.id.commentText);
        TextView commentAuthor = commentItem.findViewById(R.id.commentAuthor);
        final CommentHandler item = commentList.get(position);
        commentText.setText(item.getCommentText());
        commentAuthor.setText(item.getCommentUserName());
        return commentItem;
    }
}
