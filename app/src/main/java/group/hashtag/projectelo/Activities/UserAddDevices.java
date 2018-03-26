package group.hashtag.projectelo.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import group.hashtag.projectelo.Handlers.NewUserDevice;
import group.hashtag.projectelo.Handlers.WishlistItem;
import group.hashtag.projectelo.R;

public class UserAddDevices extends AppCompatActivity {

    TextView title;
    Spinner category, device;
    DatabaseReference mDatabase2;
    DatabaseReference mDatabase1;
    DatabaseReference addDevice;

    Toolbar toolbar;


    Map<String, Object> mapCategories;
    Map<String, Object> mapDevice;
    List<String> listCategories;
    List<String> listDevices;
    List<Map<String, Object>> listMapCategories;
    List<Map<String, Object>> listMapDevices;
    ArrayAdapter<String> categories;
    ArrayAdapter<String> devices;
    FirebaseUser auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_devices);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        title = findViewById(R.id.title_toolbar);
//
//
        auth = FirebaseAuth.getInstance().getCurrentUser();

        listCategories = new ArrayList<>();
        listMapCategories = new ArrayList<>();

        listDevices = new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        listDevices = new ArrayList<>();
        listMapDevices = new ArrayList<>();
//
        mDatabase2 = database.getReference("Device_Category");
        mDatabase1 = database.getReference("Device");
        addDevice = FirebaseDatabase.getInstance().getReference("User_device").child("Owner").child(auth.getUid());

        fetchCategories(mDatabase2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);
        category = findViewById(R.id.spinner_user_device_category);
        device = findViewById(R.id.spinner_user_product_name);
//

//
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
        categories = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listCategories);

        devices = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listDevices);


        category.setAdapter(categories);
        device.setAdapter(devices);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {// Do nothing
                } else {
                    listDevices.clear();
                    listMapDevices.clear();
                    String categoryId = searchDeviceId(adapterView.getItemAtPosition(i).toString());
                    Log.e("Here", categoryId);
//                    String categoryId = mapCategories.containsKey(adapterView.getItemIdAtPosition(i));
                    Query query = mDatabase1.orderByKey().equalTo(categoryId);
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
    public void fetchDevice(Query query) {
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDevices.add(0, "Select Device");
                for (DataSnapshot dsnp : dataSnapshot.getChildren()) {
                    for (DataSnapshot dsnp2 : dsnp.getChildren()) {
//                        Log.e("Here", "" + dsnp2);
                        mapDevice = (Map<String, Object>) dsnp2.getValue();
                        Log.e("Here", "" + mapDevice);

                        String value = mapDevice.get("DeviceSeries").toString();
                        listDevices.add(value);
                        listMapDevices.add(mapDevice);
                    }
                    Log.e("Here", "" + listMapDevices);
                    devices.notifyDataSetChanged();
//                    String value = mapCategories.get("DeviceSeries").toString();
//                    listDevices.add(value);
//                    listMapCategories.add(mapCategories);
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
                categoryId = map.get("CatId").toString();
                break;
            }

        }
        return categoryId;
    }

    public void addDevice(){

        String categoryName  = category.getSelectedItem().toString();
        String deviceName = device.getSelectedItem().toString();

        if (!TextUtils.isEmpty(deviceName)){
            String id = addDevice.push().getKey();
            NewUserDevice item = new NewUserDevice(categoryName,deviceName, auth.getUid(),id);
            addDevice.child(id).setValue(item);
            Toast.makeText(this,"Device Added",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Please select a device",Toast.LENGTH_SHORT).show();
        }
    }
}
