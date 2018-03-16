package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import group.hashtag.projectelo.R;

/**
 * Created by amant on 16/03/18.
 */

public class Wishlist extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton addDevice;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_main);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        addDevice = findViewById(R.id.wlfab);
        toolbar = findViewById(R.id.wlToolbar);
        title = findViewById(R.id.title_toolbar);

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

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItem = new Intent(Wishlist.this,WishlistAddItem.class);
                startActivity(addItem);
            }
        });
    }
}
