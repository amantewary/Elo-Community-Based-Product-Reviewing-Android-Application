package group.hashtag.projectelo.Activities;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import group.hashtag.projectelo.Handlers.UserHandler;
import group.hashtag.projectelo.R;

/**
 * Class for editing user information
 */

public class EditUserProfile extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;

    EditText editUsername;
    Spinner editCountry, editMonth, editYear, editDate;
    EditText editWebLink;
    Button save;
    Button cancel;
    DatabaseReference userRef;
    FirebaseUser auth;
    String stringUserGender, stringUserName, stringUserEmail, stringWebLink, stringUserLike, stringUserPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        auth = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("users");

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title_toolbar);
        editUsername = findViewById(R.id.edit_username);
        editCountry = findViewById(R.id.edit_spinnercountry);
        editMonth = findViewById(R.id.edit_dob_month);
        editYear = findViewById(R.id.edit_dob_year);
        editDate = findViewById(R.id.edit_dob_date);
        editWebLink = findViewById(R.id.edit_webLink);
        save = findViewById(R.id.btnSave);
        cancel = findViewById(R.id.editCancel);

        Intent intent = getIntent();
        stringUserName = intent.getStringExtra("username");
        stringUserEmail = intent.getStringExtra("useremail");
        stringUserGender = intent.getStringExtra("usergender");
        stringWebLink = intent.getStringExtra("userweblink");
        stringUserLike = intent.getStringExtra("userlike");
        stringUserPic = intent.getStringExtra("userpic");

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

        editWebLink.setText(stringWebLink);
        editUsername.setText(stringUserName);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = auth.getUid();
                String newUsername = editUsername.getText().toString().trim();
                String newCountry = editCountry.getSelectedItem().toString();
                String newMonth = editMonth.getSelectedItem().toString();
                String newYear = editYear.getSelectedItem().toString();
                String newDate = editDate.getSelectedItem().toString();
                String newWebLink = editWebLink.getText().toString();

                if (!TextUtils.isEmpty(newUsername)) {

                    UserHandler newDetails = new UserHandler(newUsername, id, newCountry, newMonth, newYear, newWebLink, stringUserEmail, stringUserGender, newDate, stringUserLike, stringUserPic);
                    userRef.child(id).setValue(newDetails);
                    finish();

                } else {

                    editUsername.setError("Username cannot be empty.");

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
