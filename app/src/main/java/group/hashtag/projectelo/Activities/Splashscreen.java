package group.hashtag.projectelo.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import group.hashtag.projectelo.R;

/**
 * Created by Manish Erramelli on 10/02/18.
 */

public class Splashscreen extends AppCompatActivity {

    LinearLayout myView;
    Animator animator;
    ImageView logo;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        myView = findViewById(R.id.circularRevealLayout);
        logo = findViewById(R.id.logo);
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
        myView.post(new Runnable() {
            @Override
            public void run() {

                int cx = (myView.getLeft() + myView.getRight()) / 2;
                int cy = (myView.getTop() + myView.getBottom()) / 2;
                // get the final radius for the clipping circle
                int dx = Math.max(cx, myView.getWidth() - cx);
                int dy = Math.max(cy, myView.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);
                animator =
                        ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                // Android native animator

                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(1200);
                animator.start();

            }
        });
    }

    private class LogoLauncher extends Thread {
        public void run() {

            try {

                sleep(1300);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (auth.getCurrentUser() != null)
            {
                startActivity(new Intent(Splashscreen.this, HomeActivity.class));
                finish();
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(),  OnboardingAvtivity.class);
                startActivity(intent);
                finish();
            }

        }

    }
}