package group.hashtag.projectelo.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import group.hashtag.projectelo.Handlers.UserHandler;
import group.hashtag.projectelo.R;

/**
 * Created by amant on 10/02/18.
 */

public class UserProfile extends AppCompatActivity {
    TextView title, usernameText;
    private DatabaseReference mDatabase;

    private ImageButton messages;
    private ImageButton reviews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Todo: May have to figure out a better way of storing the name coz it is updating with a long delay

        messages = findViewById(R.id.imageButton);
        reviews= findViewById(R.id.imageButton2);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.user_profile);


        title = findViewById(R.id.title_toolbar);
        usernameText = findViewById(R.id.usernameTextView);
        mDatabase.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserHandler user = dataSnapshot.getValue(UserHandler.class);
                usernameText.setText(user.getName());
                Log.e(UserProfile.class.getCanonicalName(), "Username: " + user.getName() + ", email " + user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(UserProfile.class.getCanonicalName(), "Failed to read value.", error.toException());
            }
        });


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
    }
}
