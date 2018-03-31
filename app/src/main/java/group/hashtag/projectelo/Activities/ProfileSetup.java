package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import group.hashtag.projectelo.Handlers.UserHandler;
import group.hashtag.projectelo.R;

public class ProfileSetup extends AppCompatActivity {

    TextView username;
    Spinner country, month, year, date, gender;
    EditText webLink;
    Button next;
    DatabaseReference userRef;
    FirebaseUser auth;
    String stringUserId, stringUserName, stringUserEmail;
    Double likes;
    CircleImageView displayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        userRef = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance().getCurrentUser();

        username = findViewById(R.id.username_textview);
        country = findViewById(R.id.spinnercountry);
        date = findViewById(R.id.dob_date);
        month = findViewById(R.id.dob_month);
        year = findViewById(R.id.dob_year);
        gender = findViewById(R.id.spinner_gender);
        webLink = findViewById(R.id.webLink);
        next = findViewById(R.id.btnNext);
        displayImage = findViewById(R.id.userDisplayPic);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stringUserId = bundle.getString("userId");
            stringUserName = bundle.getString("userName");
            stringUserEmail = bundle.getString("userEmail");
        }
        username.setText(stringUserName);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("Male")) {
                    // Do nothing
                    displayImage.setImageDrawable(getResources().getDrawable(R.drawable.male));
                } else if (adapterView.getItemAtPosition(i).toString().equals("Female")){
                    displayImage.setImageDrawable(getResources().getDrawable(R.drawable.female));

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserDetail();
            }
        });

    }

    public void addUserDetail() {
        String userCountry = country.getSelectedItem().toString();
        String userBirthDate = date.getSelectedItem().toString();
        String userBirthMonth = month.getSelectedItem().toString();
        String userBirthYear = year.getSelectedItem().toString();
        String userGender = gender.getSelectedItem().toString();
        String userWebLink = webLink.getText().toString();
        likes = 0.0;
        if (!TextUtils.isEmpty(userCountry) && !TextUtils.isEmpty(userBirthMonth) && !TextUtils.isEmpty(userBirthYear)) {
            UserHandler item = new UserHandler(stringUserName, stringUserId, userCountry, userBirthMonth, userBirthYear, userWebLink, stringUserEmail, userGender, userBirthDate, likes.toString());
            userRef.child(stringUserId).setValue(item);
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}
