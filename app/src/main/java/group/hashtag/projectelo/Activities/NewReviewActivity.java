package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import group.hashtag.projectelo.Handlers.NewReviewHandler;
import group.hashtag.projectelo.R;

/**
 * Created by nikhilkamath on 01/03/18.
 */

public class NewReviewActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner category, device;
    EditText newReviewTitle;
    EditText newReviewDescription;
    TextView title;
    DatabaseReference mDatabase2;
    DatabaseReference mDatabase1;
    DatabaseReference reviewDatabase;

    Map<String, Object> mapCategories;
    Map<String, Object> mapDevice;
    List<String> listCategories;
    List<String> listDevices;
    List<Map<String, Object>> listMapCategories;
    List<Map<String, Object>> listMapDevices;
    ArrayAdapter<String> categories;
    ArrayAdapter<String> devices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_review_layout);

        reviewDatabase = FirebaseDatabase.getInstance().getReference("newReview");
        title = findViewById(R.id.title_toolbar);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");


        listCategories = new ArrayList<>();
        listMapCategories = new ArrayList<>();

        listDevices = new ArrayList<>();
        listMapDevices = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase2 = database.getReference("Device_Category");
        mDatabase1 = database.getReference("Device");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);

        fetchCategories(mDatabase2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        category = findViewById(R.id.spinner_device_category);
        device = findViewById(R.id.spinner_product_name);
        newReviewTitle = findViewById(R.id.new_review_title);
        newReviewDescription = findViewById(R.id.new_review_description);

//        listDevices = new ArrayList<String>(mapDevice.keySet());

        categories = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listCategories);

        devices = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listCategories);


        category.setAdapter(categories);
        device.setAdapter(categories);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {// Do nothing
                } else {
                    String categoryId = searchDeviceId(adapterView.getItemAtPosition(i).toString());
                    Log.e("Here", categoryId);
//                    String categoryId = mapCategories.containsKey(adapterView.getItemIdAtPosition(i));
                    Query query = mDatabase1.orderByChild("Dev_C_Id").equalTo(categoryId);
                    fetchDevice(query);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    // Do nothing
                } else {
                    Log.e(NewReviewActivity.class.getCanonicalName(), "Position" + i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_post) {
            addReview();
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchCategories(DatabaseReference mDatabase2) {
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCategories.add(0, "Select Category");
                for (DataSnapshot dsnp : dataSnapshot.getChildren()) {
                    mapCategories = (Map<String, Object>) dsnp.getValue();
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

    public void fetchDevice(Query query) {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsnp : dataSnapshot.getChildren()) {
                    mapDevice = (Map<String, Object>) dsnp.getValue();
                    Log.e("Here", "" + mapDevice);
                    String value = mapCategories.get("CatName").toString();
                    listDevices.add(value);
                    listMapCategories.add(mapCategories);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });
    }

    public String searchDeviceId(String categoryName) {
        String categoryId = "";
        for (Map<String, Object> map : listMapCategories) {
            if (categoryName.equals(map.get("CatName"))) {
                categoryId = map.get("Dev_C_Id").toString();
                break;
            }

        }
        return categoryId;
    }

    public void addReview(){
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        String userId = auth.getUid();
        String reviewTitle = newReviewTitle.getText().toString().trim();
        String reviewDescription = newReviewDescription.getText().toString().trim();
        String deviceCategory = category.getSelectedItem().toString();
        String deviceName = device.getSelectedItem().toString();

        if(!TextUtils.isEmpty(reviewTitle) && !TextUtils.isEmpty(reviewDescription)){
            String id = reviewDatabase.push().getKey();
            NewReviewHandler newReview = new NewReviewHandler(userId,id,reviewTitle,reviewDescription,deviceName,deviceCategory);
            reviewDatabase.child(id).setValue(newReview);
            Toast.makeText(this,"New Review Added",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            if(reviewTitle.matches("")){
                newReviewTitle.setError("Title cannot be empty");
                return;
            }
            if(reviewDescription.matches("")){
                newReviewDescription.setError("Description cannot be empty");
                return;
            }
        }
    }
}
