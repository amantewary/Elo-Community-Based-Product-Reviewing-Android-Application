package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import group.hashtag.projectelo.R;

public class ViewUserProfile extends AppCompatActivity {

    TextView title;
    Toolbar toolbar;

    TextView viewUserName;
    Button followButton;
    private CircleImageView userProfilePic;
    String stringReviewUserName;
    String stringReviewUserId;
    String follow_status;

    DatabaseReference followRef;
    FirebaseUser auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.view_user_profile);
        follow_status="not_following";
        followRef = FirebaseDatabase.getInstance().getReference("follow");
        Intent intent = getIntent();
        stringReviewUserName = intent.getStringExtra("reviewUser");
        stringReviewUserId = intent.getStringExtra("reviewUserId");
        //Log.e("ID",stringReviewUserId);

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        title = findViewById(R.id.title_toolbar);
        toolbar = findViewById(R.id.toolbar);
        viewUserName = findViewById(R.id.viewUserName);
        followButton = findViewById(R.id.btnFollow);
        userProfilePic = findViewById(R.id.viewUserDisplayPic);
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
        //TODO: Displaying name of the review author on profile page.
        viewUserName.setText(stringReviewUserName);

        followRef.child(stringReviewUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot followers : dataSnapshot.getChildren()){
                    String followerId = followers.getKey();
                    if(followerId.equals(auth.getUid())){
                        follow_status = "following";
                        followButton.setText("Unfollow");
                    }else{
                        follow_status = "not_following";
                        followButton.setText("Follow Me");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(follow_status.equals("not_following")){
                    String date = DateFormat.getDateTimeInstance().format(new Date());
                    String since = "Following Since "+date;
                    followRef.child(stringReviewUserId).child(auth.getUid()).setValue(since);
                    follow_status = "following";
                    followButton.setText("Unfollow");
                }else{
                    followRef.child(stringReviewUserId).child(auth.getUid()).removeValue();
                    follow_status = "not_following";
                    followButton.setText("Follow Me");
                }
            }
        });
    }

}