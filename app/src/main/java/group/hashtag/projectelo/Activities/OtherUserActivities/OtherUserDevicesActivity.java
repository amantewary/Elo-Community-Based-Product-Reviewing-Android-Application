package group.hashtag.projectelo.Activities.OtherUserActivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import group.hashtag.projectelo.Handlers.NewUserDevice;
import group.hashtag.projectelo.R;

public class OtherUserDevicesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_other_user_devices);
        title = findViewById(R.id.title_toolbar);

        Intent intent = getIntent();
        String stringUserid = intent.getStringExtra("otherUserId");

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        Toolbar toolbar = findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);

        newDeviceListView = findViewById(R.id.odlListView);
        newUserDevices = new ArrayList<NewUserDevice>();
        arrayAdapter = new CustomDevicesAdapter(this, newUserDevices);

        deviceRef = FirebaseDatabase.getInstance().getReference("User_device").child("Owner").child(stringUserid);

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

    public class CustomDevicesAdapter extends ArrayAdapter<NewUserDevice> {

        List<NewUserDevice> userDeviceList;

        public CustomDevicesAdapter(@NonNull Context context, List<NewUserDevice> newUserDevices) {
            super(context, 0, newUserDevices);
            this.userDeviceList = newUserDevices;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View followersView = convertView;
            if (followersView == null) {
                followersView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.other_user_devices_item, parent, false);
            }
            final NewUserDevice newUserDevice = userDeviceList.get(position);
            TextView deviceName = followersView.findViewById(R.id.odlDeviceName);
            TextView deviceCategory = followersView.findViewById(R.id.odlCategory);
            deviceName.setText(newUserDevice.deviceName);
            deviceCategory.setText(newUserDevice.category);
            return followersView;


        }
    }
}
