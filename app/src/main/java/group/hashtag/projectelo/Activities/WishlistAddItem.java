package group.hashtag.projectelo.Activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import group.hashtag.projectelo.R;

/**
 * Created by amant on 16/03/18.
 */
public class WishlistAddItem extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;

    EditText wlAddDeviceName;
    Spinner wlCatSpinner;

    List<String> listCategories;
    ArrayAdapter<String> categories;

    DatabaseReference categoriesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_add_item);
        categoriesRef = FirebaseDatabase.getInstance().getReference("Device_Category");
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        toolbar = findViewById(R.id.wlAddToolbar);
        title = findViewById(R.id.title_toolbar);

        wlAddDeviceName = findViewById(R.id.wlAddDeviceName);
        wlCatSpinner = findViewById(R.id.wlCatSpinner);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_wishlist_item, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_save){

        }

        return super.onOptionsItemSelected(item);
    }
}
