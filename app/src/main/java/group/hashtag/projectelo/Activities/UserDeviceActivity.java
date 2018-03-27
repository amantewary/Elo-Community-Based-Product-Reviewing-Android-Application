package group.hashtag.projectelo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

import group.hashtag.projectelo.Handlers.NewUserDevice;
import group.hashtag.projectelo.Handlers.UserProfileFollowersHandlers;
import group.hashtag.projectelo.Handlers.WishlistItem;
import group.hashtag.projectelo.R;

public class UserDeviceActivity extends AppCompatActivity {

    TextView title;
    Map<String, Object> mapUserDevices;
//    Map<String, Object> mapUserDevices;
    DatabaseReference deviceRef;

    List<NewUserDevice> newUserDevices;
    ListView newDeviceListView;
    CustomDevicesAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_user_device);
        title = findViewById(R.id.title_toolbar);

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);

        newDeviceListView = findViewById(R.id.dlListView);
        newUserDevices = new ArrayList<NewUserDevice>();
        arrayAdapter = new CustomDevicesAdapter(this, newUserDevices);

        deviceRef = FirebaseDatabase.getInstance().getReference("User_device").child("Owner").child(auth.getUid());
        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.dlfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserAddDevices.class));
            }
        });

        deviceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newUserDevices.clear();
                for(DataSnapshot dlSnapshot : dataSnapshot.getChildren()){
                    NewUserDevice dlItem = dlSnapshot.getValue(NewUserDevice.class);
                    newUserDevices.add(dlItem);
                }
                CustomDevicesAdapter dlAdapter= new CustomDevicesAdapter(UserDeviceActivity.this,newUserDevices);
                newDeviceListView.setAdapter(dlAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Wishlist.class.getCanonicalName(), "Failed to read value.", databaseError.toException());
            }
        });
    }



    public class CustomDevicesAdapter extends ArrayAdapter<NewUserDevice> {

        List<NewUserDevice> newUserDeviceList;

        public CustomDevicesAdapter(@NonNull Context context, List<NewUserDevice> newUserDevices) {
            super(context, 0,newUserDevices);
            this.newUserDeviceList = newUserDevices;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View deviceView = convertView;
            if (deviceView == null) {
                deviceView = LayoutInflater.from(UserDeviceActivity.this).inflate(R.layout.user_device_list_item, parent, false);
            }
            final NewUserDevice newUserDevice = newUserDeviceList.get(position);

            final ImageButton deleteDevices = deviceView.findViewById(R.id.dlItemDelete);
            deleteDevices.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteDevicesFunction(newUserDevice.deviceId);
                }
            });
            TextView deviceName = deviceView.findViewById(R.id.dlDeviceName);
            TextView categoryName = deviceView.findViewById(R.id.dlCategory);
            deviceName.setText(newUserDevice.deviceName);
            categoryName.setText(newUserDevice.category);
            return deviceView;


        }
    }

    public void deleteDevicesFunction(String deviceId){
        if (arrayAdapter.getCount() <= 1){
            Toast.makeText(getApplicationContext(),"You need to have at least one device listed",Toast.LENGTH_SHORT).show();
        }else{
            Log.e(UserDeviceActivity.class.getCanonicalName(),"Here");
            FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
            deviceRef = FirebaseDatabase.getInstance().getReference("User_device").child("Owner").child(auth.getUid());
            deviceRef.child(deviceId).removeValue();
            arrayAdapter.notifyDataSetChanged();
        }
    }

}
