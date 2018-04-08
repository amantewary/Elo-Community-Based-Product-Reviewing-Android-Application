package group.hashtag.projectelo.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import group.hashtag.projectelo.R;
import in.championswimmer.sfg.lib.SimpleFingerGestures;

/**
 * Created by Manish Erramelli on 27/02/18.
 * This class fetch the featured article written on the latest
 * technology advancement. It just fetch as the data and shows on the home screen.
 *  Adapted from: championswimmer/SimpleFingerGestures_Android_Library", GitHub, 2018. [Online]. Available:  https://github.com/championswimmer/SimpleFingerGestures_Android_Library. [Accessed: 31- Mar- 2018]].
 */

public class FeaturedArticle extends AppCompatActivity {
    TextView title;
    TextView FeaturedTitle;
    TextView FeaturedContent;

    String FeaturedTitleString = "";
    String FeaturedContentString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.featured_article_layout);
        SimpleFingerGestures mySfg = new SimpleFingerGestures();

        title = findViewById(R.id.title_toolbar);
        FeaturedTitle = findViewById(R.id.featured_view_title);
        FeaturedContent = findViewById(R.id.featured_view_content);
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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            FeaturedTitleString = bundle.getString("FeaturedTitle");
            FeaturedContentString = bundle.getString("FeaturedContent");
        }


        FeaturedTitle.setText(FeaturedTitleString);
        FeaturedContent.setText(FeaturedContentString);

    }
}
