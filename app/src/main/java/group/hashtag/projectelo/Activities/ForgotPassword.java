package group.hashtag.projectelo.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import group.hashtag.projectelo.R;

/**
 * Created by nikhilkamath on 01/03/18.
 */

public class ForgotPassword extends AppCompatActivity {

    private TextInputLayout forgotEmail;
    private EditText forgotEmailEditText;
    private Button forgotButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        final FirebaseAuth auth = FirebaseAuth.getInstance();

        forgotEmail = findViewById(R.id.input_layout_email_forgot);
        forgotEmailEditText = findViewById(R.id.forgot_email);

        forgotButton = findViewById(R.id.button_forgot_password);

        forgotEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (forgotEmailEditText.getText().toString().equals("")) {
                    forgotEmail.setError("Don't leave this field empty");
                } else if (!isEmailValid(forgotEmailEditText.getText().toString())) {
                    forgotEmail.setError("Please enter a valid email Id");
                } else {
                    auth.sendPasswordResetEmail(forgotEmailEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.e(ForgotPassword.class.getCanonicalName(), "Email sent.");
                                        forgotEmail.setError("");
                                        //Add a snackbar
                                        hideKeyboard(view);
                                        Snackbar loginGuide = Snackbar.make(findViewById(R.id.linear_layout_forgot_password),
                                                "Go back to login screen", Snackbar.LENGTH_INDEFINITE);
                                        loginGuide.setAction("Okay!", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                        loginGuide.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                        loginGuide.show();
                                    }
                                }
                            });
                }
            }
        });


    }


    // Validation code taken from:- https://stackoverflow.com/a/6119777/3966666
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    // https://stackoverflow.com/a/19828165/3966666
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
