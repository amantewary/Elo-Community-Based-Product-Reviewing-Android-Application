package group.hashtag.projectelo.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import group.hashtag.projectelo.R;

public class ViewUserProfile extends AppCompatActivity {

    TextView title;
    Toolbar toolbar;

    TextView viewUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_user_profile);

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        title = findViewById(R.id.title_toolbar);
        toolbar = findViewById(R.id.toolbar);
        viewUserName = findViewById(R.id.viewUserName);
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