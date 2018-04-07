package group.hashtag.projectelo.Activities.OtherUserActivities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import group.hashtag.projectelo.R;

public class ViewUserProfile extends AppCompatActivity {

    TextView title;
    Toolbar toolbar;

    TextView viewUserName;
    TextView viewUserCountryText;
    TextView viewUserDateText;
    TextView viewUserMonthText;
    TextView viewUserYearText;
    TextView viewUserEmailText;
    TextView viewUserWeblinkText;
    TextView viewUserGenderText;
    TextView viewUserDeviceCounter;
    TextView viewUserWishlistCounter;
    TextView viewUserFollowersCounter;
    Button followButton;
    String stringReviewUserName;
    String stringReviewUserId;
    String follow_status;
    String stringReviewUserEmail;
    String stringReviewUserDobDate;
    String stringReviewUserDobMonth;
    String stringReviewUserDobYear;
    String stringReviewUserGender;
    String stringReviewUserWebLink;
    String stringReviewUserCountry;
    String otherUserDisplay;
    IconRoundCornerProgressBar userProgress;
    Map<String, Object> mapUser;
    DatabaseReference followRef;
    DatabaseReference otherUserWishList;
    DatabaseReference otherUserFollowers;
    DatabaseReference otherUserDevices;
    DatabaseReference otherUserRef;
    FirebaseUser auth;
    private CircleImageView userProfilePic;
    private LinearLayout btnOtherUserWishlist, btnOtherUserDevices, btnOtherUserFollowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.view_user_profile);
        follow_status = "not_following";
        followRef = FirebaseDatabase.getInstance().getReference("follow");
        otherUserRef = FirebaseDatabase.getInstance().getReference("users");
        Intent intent = getIntent();
        stringReviewUserName = intent.getStringExtra("reviewUser");
        stringReviewUserId = intent.getStringExtra("reviewUserId");
        stringReviewUserDobDate = intent.getStringExtra("reviewUserDate");
        stringReviewUserDobMonth = intent.getStringExtra("reviewUserMonth");
        stringReviewUserDobYear = intent.getStringExtra("reviewUserYear");
        stringReviewUserCountry = intent.getStringExtra("reviewUserCountry");
        stringReviewUserGender = intent.getStringExtra("reviewUserGender");
        stringReviewUserWebLink = intent.getStringExtra("reviewUserWebLink");
        stringReviewUserEmail = intent.getStringExtra("reviewUserEmail");

        //Log.e("ID",stringReviewUserId);


        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        title = findViewById(R.id.title_toolbar);
        toolbar = findViewById(R.id.toolbar);
        viewUserName = findViewById(R.id.viewUserName);
        viewUserCountryText = findViewById(R.id.view_country_textview);
        viewUserDateText = findViewById(R.id.view_dob_date_textview);
        viewUserMonthText = findViewById(R.id.view_dob_month_textview);
        viewUserYearText = findViewById(R.id.view_dob_year_textview);
        viewUserEmailText = findViewById(R.id.view_email_textview);
        viewUserWeblinkText = findViewById(R.id.view_weblink_textview);
        viewUserGenderText = findViewById(R.id.view_gender_textview);
        followButton = findViewById(R.id.btnFollow);
        userProfilePic = findViewById(R.id.viewUserDisplayPic);
        title.setTypeface(ReemKufi_Regular);
        btnOtherUserFollowers = findViewById(R.id.other_user_followers);
        btnOtherUserDevices = findViewById(R.id.other_user_devices);
        btnOtherUserWishlist = findViewById(R.id.other_user_wishlist);
        viewUserWishlistCounter = findViewById(R.id.other_user_wishlist_count_text_view);
        viewUserDeviceCounter = findViewById(R.id.other_user_devices_count_text_view);
        viewUserFollowersCounter = findViewById(R.id.other_user_followers_count_text_view);


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
        viewUserEmailText.setText(stringReviewUserEmail);
        viewUserCountryText.setText(stringReviewUserCountry);
        viewUserDateText.setText(stringReviewUserDobDate);
        viewUserMonthText.setText(stringReviewUserDobMonth);
        viewUserYearText.setText(stringReviewUserDobYear);
        viewUserWeblinkText.setText(stringReviewUserWebLink);
        viewUserGenderText.setText(stringReviewUserGender);


        otherUserWishList = FirebaseDatabase.getInstance().getReference("User_device").child("Wishlist").child(stringReviewUserId);
        otherUserFollowers = FirebaseDatabase.getInstance().getReference("follow").child(stringReviewUserId);
        otherUserDevices = FirebaseDatabase.getInstance().getReference("User_device").child("Owner").child(stringReviewUserId);

        otherUserWishList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long wlCounter = dataSnapshot.getChildrenCount();
                viewUserWishlistCounter.setText(Long.toString(wlCounter));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        otherUserFollowers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long flCounter = dataSnapshot.getChildrenCount();
                viewUserFollowersCounter.setText(Long.toString(flCounter));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        otherUserDevices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long dvCounter = dataSnapshot.getChildrenCount();
                viewUserDeviceCounter.setText(Long.toString(dvCounter));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        userProgress = findViewById(R.id.view_profile_progress);
        otherUserRef.child(stringReviewUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapUser = (Map<String, Object>) dataSnapshot.getValue();
                String userLike = mapUser.get("likes").toString();
                otherUserDisplay = mapUser.get("Display_Pic").toString();
                loadDisplayPics(otherUserDisplay);
                userProgress.setProgress(Integer.parseInt(userLike));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });

        btnOtherUserWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wlintent = new Intent(getApplicationContext(), OtherUserWishlist.class);
                wlintent.putExtra("otherUserId", stringReviewUserId);
                startActivity(wlintent);
            }
        });

        btnOtherUserFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent flintent = new Intent(getApplicationContext(), OtherUserFollowers.class);
                flintent.putExtra("otherUserId", stringReviewUserId);
                startActivity(flintent);
            }
        });

        btnOtherUserDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dlintent = new Intent(getApplicationContext(), OtherUserFollowers.class);
                dlintent.putExtra("otherUserId", stringReviewUserId);
                startActivity(dlintent);
            }
        });

        followRef.child(stringReviewUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot followers : dataSnapshot.getChildren()) {
                    String followerId = followers.getKey();
                    if (followerId.equals(auth.getUid())) {
                        follow_status = "following";
                        followButton.setText("Unfollow");
                    } else {
                        follow_status = "not_following";
                        followButton.setText("Follow Me");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        loadDisplayPics(otherUserDisplay);


        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow_status.equals("not_following")) {
                    String date = DateFormat.getDateTimeInstance().format(new Date());
                    String since = "Following Since " + date;
                    followRef.child(stringReviewUserId).child(auth.getUid()).setValue(since);
                    follow_status = "following";
                    followButton.setText("Unfollow");
                } else {
                    followRef.child(stringReviewUserId).child(auth.getUid()).removeValue();
                    follow_status = "not_following";
                    followButton.setText("Follow Me");
                }
            }
        });


    }

    public void loadDisplayPics(String url) {
        Log.e("load", url);
        Picasso.get().load(url).fit().error(R.drawable.cover).placeholder(R.drawable.female).into(userProfilePic);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }


}