package group.hashtag.projectelo.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import group.hashtag.projectelo.Handlers.UserHandler;
import group.hashtag.projectelo.R;

public class ProfileSetup extends AppCompatActivity {

    TextView username;
    DatabaseReference userRef;
    FirebaseUser auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        userRef = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance().getCurrentUser();

        username = findViewById(R.id.username_textview);
        userRef.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserHandler user = dataSnapshot.getValue(UserHandler.class);
                username.setText(user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
