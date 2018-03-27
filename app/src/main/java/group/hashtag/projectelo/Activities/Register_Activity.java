package group.hashtag.projectelo.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import group.hashtag.projectelo.Handlers.UserHandler;
import group.hashtag.projectelo.R;

/**
 * Created by nikhilkamath on 11/02/18.
 */

public class Register_Activity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputUsername, inputConfirmPassword;
    private TextInputLayout emailInput, passwordInput, usernameInput, confirmPasswordInput;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    ImageView googleButton;
    private DatabaseReference mDatabase;
    int RC_SIGN_IN = 111;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        googleButton = findViewById(R.id.google_button);


        mDatabase = FirebaseDatabase.getInstance().getReference("users");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Register_Activity.this, HomeActivity.class));
            finish();
        }

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        inputUsername = findViewById(R.id.username);
        emailInput = findViewById(R.id.input_layout_email_register);
        passwordInput = findViewById(R.id.input_layout_password_register);
        usernameInput = findViewById(R.id.input_layout_username_register);
        confirmPasswordInput = findViewById(R.id.input_layout_confirm_password_register);
        inputConfirmPassword = findViewById(R.id.confirm_password);


        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String name = inputUsername.getText().toString().trim();

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String confirmPassword = inputConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    usernameInput.setError("Enter a username");
                    emailInput.setError(null);
                    passwordInput.setError(null);
                    confirmPasswordInput.setError(null);
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    passwordInput.setError(null);
                    usernameInput.setError(null);
                    confirmPasswordInput.setError(null);
                    emailInput.setError("Enter email address!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    emailInput.setError(null);
                    usernameInput.setError(null);
                    confirmPasswordInput.setError(null);
                    passwordInput.setError("Enter correct Password");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    emailInput.setError(null);
                    usernameInput.setError(null);
                    confirmPasswordInput.setError("Enter correct Password");
                    passwordInput.setError(null);
                    return;
                }

                if (password.length() < 6) {
                    emailInput.setError(null);
                    usernameInput.setError(null);
                    confirmPasswordInput.setError(null);
                    passwordInput.setError("Please keep your length less than 6");
                    return;
                }
                if (!isEmailValid(email)) {
                    passwordInput.setError(null);
                    usernameInput.setError(null);
                    confirmPasswordInput.setError(null);
                    emailInput.setError("Enter a valid email address");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    passwordInput.setError("Re-enter the password");
                    usernameInput.setError(null);
                    inputConfirmPassword.setText("");
                    inputConfirmPassword.setText("");
                    confirmPasswordInput.setError("Re-enter the password");
                    emailInput.setError(null);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        emailInput.setError(null);
                                        passwordInput.setError(null);
                                        usernameInput.setError(null);
                                        confirmPasswordInput.setError(null);
                                        String userId = user.getUid();
                                        UserHandler userHandler = new UserHandler();
                                        userHandler.setName(name);
                                        Log.e(Register_Activity.class.getCanonicalName(),email);
                                        UserHandler userhandler = new UserHandler(name, userId, email);
                                        hideKeyboard(v);

                                        mDatabase.child(userId).setValue(userhandler);


                                        startActivity(new Intent(getApplicationContext(), ProfileSetup.class));
                                        finish();
                                    }


                                }
                            }
                        });


            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(Register_Activity.class.getCanonicalName(), "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e(Register_Activity.class.getCanonicalName(), "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(Register_Activity.class.getCanonicalName(), "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                UserHandler userHandler = new UserHandler();
                                userHandler.setName(user.getDisplayName());
                                Log.e(Register_Activity.class.getCanonicalName(), user.getEmail());
                                UserHandler userhandler = new UserHandler(user.getDisplayName(), user.getEmail(), userId);

                                mDatabase.child(userId).setValue(userhandler);


                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(Register_Activity.class.getCanonicalName(), "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    // https://stackoverflow.com/a/19828165/3966666
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // Validation code taken from:- https://stackoverflow.com/a/6119777/3966666
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
