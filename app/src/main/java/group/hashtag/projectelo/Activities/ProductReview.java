package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import group.hashtag.projectelo.R;

public class ProductReview extends AppCompatActivity {


    TextView title;
    TextView content;
    TextView reviewtitle;
    TextView reviewDevice;

    String stringTitle;
    String stringContent;
    String stringCategory;
    String stringReviewAuthor;

    CircleImageView userAuthorImage;
    Map<String, Object> mapUser;
    String userName;
    DatabaseReference userRef;
    FirebaseUser auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.review_layout);
        title = findViewById(R.id.title_toolbar);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        userAuthorImage = findViewById(R.id.reviewAuthor);
        reviewtitle = findViewById(R.id.textView5);
        content = findViewById(R.id.textView4);
        reviewDevice = findViewById(R.id.reviewDeviceName);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stringReviewAuthor = bundle.getString("reviewAuthor");
            stringTitle = bundle.getString("reviewTitle");
            stringContent = bundle.getString("reviewContent");
            stringCategory = bundle.getString("category");
        }


        reviewtitle.setText(stringTitle);
        content.setText(stringContent);
        reviewDevice.setText(stringCategory);
        userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.child(stringReviewAuthor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapUser = (Map<String, Object>) dataSnapshot.getValue();
                Log.e("Here", "" + mapUser);
                userName = mapUser.get("name").toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", ""+databaseError);

            }
        });
        userAuthorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = auth.getUid();
                Log.e(stringReviewAuthor,userId);
                //TODO: This condition is not working. Else statement is executed everytime.
                if (stringReviewAuthor.equals(userId)){
                    Intent userProfile = new Intent(ProductReview.this, UserProfile.class);
                    startActivity(userProfile);
                }else {
                    Intent intent = new Intent(ProductReview.this, ViewUserProfile.class);
                    intent.putExtra("reviewUser", userName);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wishlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
