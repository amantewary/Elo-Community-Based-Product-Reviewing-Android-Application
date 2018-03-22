package group.hashtag.projectelo.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import group.hashtag.projectelo.Handlers.NewUserDevice;
import group.hashtag.projectelo.Handlers.UserProfileFollowersHandlers;
import group.hashtag.projectelo.R;

public class UserDeviceActivity extends AppCompatActivity {

    TextView title;
    Map<String, Object> mapUser;
    Map<String, Object> mapFollower;
    DatabaseReference userRef;
    DatabaseReference followRef;

    List<NewUserDevice> newUserDevices;
    ListView newDeviceListView;
    CustomDevicesAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_device);
        title = findViewById(R.id.title_toolbar);

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);

        newDeviceListView = findViewById(R.id.dlListView);
        newUserDevices = new ArrayList<NewUserDevice>();
        arrayAdapter = new CustomDevicesAdapter(this, newUserDevices);


        
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                deviceView = LayoutInflater.from(UserDeviceActivity.this).inflate(R.layout.follower_list_item, parent, false);
            }
            final NewUserDevice newUserDevice = newUserDeviceList.get(position);
            TextView followerName = deviceView.findViewById(R.id.flUserName);
            TextView followerDate = deviceView.findViewById(R.id.flDate);
            followerName.setText(newUserDevice.deviceName);
            followerDate.setText(newUserDevice.category);
            return deviceView;


        }
    }

}
