package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Spinner country, month, year,gender;
    EditText webLink;
    Button next;
    DatabaseReference userRef;
    FirebaseUser auth;
    String stringUserId, stringUserName, stringUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        userRef = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance().getCurrentUser();

        username = findViewById(R.id.username_textview);
        country = findViewById(R.id.spinnercountry);
        month = findViewById(R.id.dob_month);
        year = findViewById(R.id.dob_year);
        gender = findViewById(R.id.spinner_gender);
        webLink = findViewById(R.id.webLink);
        next = findViewById(R.id.btnNext);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stringUserId = bundle.getString("userId");
            stringUserName = bundle.getString("userName");
            stringUserEmail = bundle.getString("userEmail");
        }
        username.setText(stringUserName);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserDetail();
            }
        });

    }

    public void addUserDetail(){
        String userCountry = country.getSelectedItem().toString();
        String userBirthMonth = month.getSelectedItem().toString();
        String userBirthYear = year.getSelectedItem().toString();
        String userGender = gender.getSelectedItem().toString();
        String userWebLink = webLink.getText().toString();

        if(!TextUtils.isEmpty(userCountry) && !TextUtils.isEmpty(userBirthMonth) && !TextUtils.isEmpty(userBirthYear)){
            UserHandler item = new UserHandler(stringUserName, stringUserId, userCountry, userBirthMonth, userBirthYear, userWebLink, stringUserEmail, userGender);
            userRef.child(stringUserId).setValue(item);
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }else{
            Toast.makeText(this, "Error",Toast.LENGTH_SHORT).show();
        }

    }
}
