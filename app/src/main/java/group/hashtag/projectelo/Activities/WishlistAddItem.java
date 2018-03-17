package group.hashtag.projectelo.Activities;

import android.graphics.Typeface;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import group.hashtag.projectelo.Handlers.UserHandler;
import group.hashtag.projectelo.R;

/**
 * Created by amant on 16/03/18.
 */
public class WishlistAddItem extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;

    EditText wlAddDeviceName;
    Spinner wlCatSpinner;

    Map<String, Object> mapCategories;
    List<String> listCategories;
    List<Map<String, Object>> listMapCategories;
    ArrayAdapter<String> categories;

    DatabaseReference userRef;
    DatabaseReference categoriesRef;
    DatabaseReference wishlistRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        userRef = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.wishlist_add_item);

        categoriesRef = FirebaseDatabase.getInstance().getReference("Device_Category");
        wishlistRef = FirebaseDatabase.getInstance().getReference("User_device").child("Device_1").child("Wishlist").child(auth.getUid());

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        toolbar = findViewById(R.id.wlAddToolbar);
        title = findViewById(R.id.title_toolbar);

        wlAddDeviceName = findViewById(R.id.wlAddDeviceName);
        wlCatSpinner = findViewById(R.id.wlCatSpinner);

        listCategories = new ArrayList<>();
        listMapCategories = new ArrayList<>();

        fetchCategories(categoriesRef);
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

        categories = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listCategories);
        wlCatSpinner.setAdapter(categories);
        wlCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){}
                else{
                    Log.e(NewReviewActivity.class.getCanonicalName(), "Position" + position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                addDevice();
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchCategories(DatabaseReference categoriesRef) {
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCategories.add(0, "Select Category");
                for (DataSnapshot catSnapshot : dataSnapshot.getChildren()) {
                    mapCategories = (Map<String, Object>) catSnapshot.getValue();
                    String value = mapCategories.get("CatName").toString();
                    listCategories.add(value);
                    listMapCategories.add(mapCategories);
                }
                Log.e("Here", "" + listMapCategories);
                categories.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });
    }

    public void addDevice(){

        String deviceName = wlAddDeviceName.getText().toString().trim();
        String categoryName = wlCatSpinner.getSelectedItem().toString();

        if (!TextUtils.isEmpty(deviceName)){
            String id = wishlistRef.push().getKey();
            WishlistItem item = new WishlistItem(id, deviceName, categoryName);
            wishlistRef.child(id).setValue(item);
            Toast.makeText(this,"Device Added",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            wlAddDeviceName.setError("Device name cannot be empty.");
        }
    }
}
