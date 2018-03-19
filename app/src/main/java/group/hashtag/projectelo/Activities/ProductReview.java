package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import group.hashtag.projectelo.R;

public class ProductReview extends AppCompatActivity {
    TextView title;
    TextView content;
    TextView reviewtitle;
    TextView reviewDevice;

    String stringTitle;
    String stringContent;
    String stringCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        reviewtitle = findViewById(R.id.textView5);
        content = findViewById(R.id.textView4);
        reviewDevice = findViewById(R.id.reviewDeviceName);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            stringTitle = bundle.getString("reviewTitle");
            stringContent = bundle.getString("reviewContent");
            stringCategory = bundle.getString("category");
        }

        reviewtitle.setText(stringTitle);
        content.setText(stringContent);
        reviewDevice.setText(stringCategory);

    }
    public void onIconOnClick(View view){
        Intent intent = new Intent(ProductReview.this, UserProfile.class);
        startActivity(intent);
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
