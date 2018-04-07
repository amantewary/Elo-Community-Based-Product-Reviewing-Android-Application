package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import group.hashtag.projectelo.Activities.OtherUserActivities.ViewUserProfile;
import group.hashtag.projectelo.Handlers.CommentHandler;
import group.hashtag.projectelo.Handlers.UserHandler;
import group.hashtag.projectelo.R;

public class ProductReview extends AppCompatActivity {


    TextView title;
    TextView content;
    TextView reviewtitle;
    TextView reviewDevice;

    EditText commentText;
    ImageButton commentPost;
    SparkButton likeButton;
    ImageView reviewImageView;
    ListView commentListView;
    List<CommentHandler> commentHandlerList;

    String stringTitle;
    String stringContent;
    String stringCategory;
    String stringReviewAuthor;
    String stringReviewId;

    CircleImageView userAuthorImage;
    Map<String, Object> mapUser;
    Map<String, Object> mapCommentAuthor;
    String userName;
    String userWebLink;
    String userEmail;
    String userCountry;
    String userDobDate;
    String userDobMonth;
    String userDobYear;
    String userGender;
    String userId;
    String userLikenumber;
    String userPic;
    String reviewImage;
    String commentAuthorId;
    String commentAuthorName;
    String commentContent;
    Integer likeNumber;
    SlidingUpPanelLayout commentLayout;
    DatabaseReference userRef;
    DatabaseReference userLikeRef;
    DatabaseReference commentRef;
    DatabaseReference likeRef;
    FirebaseUser auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.review_layout);
        commentLayout = findViewById(R.id.sliding_review_layout);

        title = findViewById(R.id.title_toolbar);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");


        Toolbar toolbar = findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        likeButton = findViewById(R.id.spark_button);
        reviewImageView = findViewById(R.id.imageView2);
        commentListView = findViewById(R.id.commentList);
        commentHandlerList = new ArrayList<>();
        userAuthorImage = findViewById(R.id.reviewAuthor);
        reviewtitle = findViewById(R.id.textView5);
        content = findViewById(R.id.textView4);
        reviewDevice = findViewById(R.id.reviewDeviceName);
        commentText = findViewById(R.id.comment);
        commentPost = findViewById(R.id.postComment);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stringReviewAuthor = bundle.getString("reviewAuthor");
            stringTitle = bundle.getString("reviewTitle");
            stringContent = bundle.getString("reviewContent");
            stringCategory = bundle.getString("category");
            stringReviewId = bundle.getString("reviewId");
            reviewImage = bundle.getString("reviewImage");
            Picasso.get().load(reviewImage).fit().error(R.drawable.cover).placeholder(R.drawable.razer).into(reviewImageView);

        }


        reviewtitle.setText(stringTitle);
        content.setText(stringContent);
        reviewDevice.setText(stringCategory);
        userRef = FirebaseDatabase.getInstance().getReference("users");
        userLikeRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.child(stringReviewAuthor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapUser = (Map<String, Object>) dataSnapshot.getValue();
                Log.e("Here", "" + mapUser);
                userName = mapUser.get("name").toString();
                userId = mapUser.get("UserId").toString();
                userEmail = mapUser.get("email").toString();
                userCountry = mapUser.get("country").toString();
                userDobDate = mapUser.get("dob_date").toString();
                userDobMonth = mapUser.get("dob_month").toString();
                userDobYear = mapUser.get("dob_year").toString();
                userGender = mapUser.get("gender").toString();
                userWebLink = mapUser.get("webLink").toString();
                userLikenumber = mapUser.get("likes").toString();
                userPic = mapUser.get("Display_Pic").toString();
                likeNumber = Integer.parseInt(userLikenumber);
                Picasso.get().load(userPic).fit().error(R.drawable.cover).placeholder(R.drawable.female).into(userAuthorImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);

            }
        });
        userAuthorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = auth.getUid();
                Log.e(stringReviewAuthor, Id);
                if (stringReviewAuthor.equals(Id)) {
                    Intent userProfile = new Intent(ProductReview.this, UserProfile.class);
                    startActivity(userProfile);
                } else {
                    Intent intent = new Intent(ProductReview.this, ViewUserProfile.class);
                    intent.putExtra("reviewUserId", userId);
                    intent.putExtra("reviewUser", userName);
                    intent.putExtra("reviewUserCountry", userCountry);
                    intent.putExtra("reviewUserDate", userDobDate);
                    intent.putExtra("reviewUserMonth", userDobMonth);
                    intent.putExtra("reviewUserYear", userDobYear);
                    intent.putExtra("reviewUserEmail", userEmail);
                    intent.putExtra("reviewUserGender", userGender);
                    intent.putExtra("reviewUserWebLink", userWebLink);
                    startActivity(intent);
                }
            }
        });

        commentRef = FirebaseDatabase.getInstance().getReference("newComment");
        commentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
        likeRef = FirebaseDatabase.getInstance().getReference("likes");
        likeRef.child(stringReviewId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(auth.getUid())) {

                    likeButton.setChecked(true);
                } else {

                    likeButton.setChecked(false);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("HERE", "" + databaseError);
            }

        });

        likeButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, final boolean buttonState) {
                if (buttonState) {
                    String date = DateFormat.getDateTimeInstance().format(new Date());
                    String since = "Liked on " + date;
                    likeNumber = likeNumber + 1;
                    UserHandler addLike = new UserHandler(userName, userId, userCountry, userDobMonth, userDobYear, userWebLink, userEmail, userGender, userDobDate, likeNumber.toString(), userPic);
                    userLikeRef.child(userId).setValue(addLike);
                    likeRef.child(stringReviewId).child(auth.getUid()).setValue(since);
                    Log.e("Here", "ButtonState_if" + buttonState);
                } else {
                    likeRef.child(stringReviewId).child(auth.getUid()).removeValue();
                    Log.e("Here", "ButtonState_else" + buttonState);
                    likeNumber = likeNumber - 1;
                    UserHandler subLike = new UserHandler(userName, userId, userCountry, userDobMonth, userDobYear, userWebLink, userEmail, userGender, userDobDate, likeNumber.toString(), userPic);
                    userLikeRef.child(stringReviewAuthor).setValue(subLike);
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        commentRef.child(stringReviewId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentHandlerList.clear();
                for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                    CommentHandler item = commentSnapshot.getValue(CommentHandler.class);
                    commentHandlerList.add(item);
                }
                CommentAdapter adapter = new CommentAdapter(ProductReview.this, commentHandlerList);
                commentListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void addComment() {

        commentContent = commentText.getText().toString().trim();
        commentAuthorId = auth.getUid();

        if (!TextUtils.isEmpty(commentContent)) {
            userRef.child(commentAuthorId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mapCommentAuthor = (Map<String, Object>) dataSnapshot.getValue();
                    commentAuthorName = mapCommentAuthor.get("name").toString();
                    Log.e("Here", "1" + commentAuthorName);
                    String id = commentRef.push().getKey();
                    CommentHandler newComment = new CommentHandler(id, commentContent, commentAuthorId, commentAuthorName);
                    commentRef.child(stringReviewId).child(id).setValue(newComment);
                    commentText.setText("");
                    commentLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //TODO: Simple method (not showing username of comment author)
//            CommentHandler newComment = new CommentHandler(commentAuthorId, commentContent, commentAuthorId, commentAuthorName);
//            commentRef.child(stringReviewId).child(commentAuthorId).setValue(newComment);
//            commentText.setText("");
//            commentLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        } else {
            commentText.setError("Comment cannot be empty");
        }

    }

}
