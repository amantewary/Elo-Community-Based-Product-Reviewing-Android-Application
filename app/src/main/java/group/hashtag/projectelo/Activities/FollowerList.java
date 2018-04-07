package group.hashtag.projectelo.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import group.hashtag.projectelo.Handlers.UserProfileFollowersHandlers;
import group.hashtag.projectelo.R;

public class FollowerList extends AppCompatActivity {

    TextView title;
    TextView flName;
    TextView flDate;
    String followerId;
    String followerDate;
    String followerName;
    Map<String, Object> mapUser;
    Map<String, Object> mapFollower;
    DatabaseReference userRef;
    DatabaseReference followRef;

    List<UserProfileFollowersHandlers> userProfileFollowersHandlersList;
    ListView followersListView;
    CustomFollowersAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.follower_list);

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        Toolbar toolbar = findViewById(R.id.flToolbar);
        title = findViewById(R.id.title_toolbar);
        title.setTypeface(ReemKufi_Regular);

        followersListView = findViewById(R.id.flListView);
        userProfileFollowersHandlersList = new ArrayList<UserProfileFollowersHandlers>();
        arrayAdapter = new CustomFollowersAdapter(this, userProfileFollowersHandlersList);
        followersListView.setAdapter(arrayAdapter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        followRef = FirebaseDatabase.getInstance().getReference("follow");
        followRef.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot followers : dataSnapshot.getChildren()) {
                    followerId = followers.getKey();
                    followerDate = followers.getValue().toString();
                    userRef = FirebaseDatabase.getInstance().getReference("users");
                    userRef.child(followerId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mapUser = (Map<String, Object>) dataSnapshot.getValue();
                            Log.e("Here", "" + mapUser);
                            followerName = mapUser.get("name").toString();
                            final UserProfileFollowersHandlers userProfileFollowersHandlers = new UserProfileFollowersHandlers(followerName, followerDate);
                            userProfileFollowersHandlersList.add(userProfileFollowersHandlers);
                            arrayAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("Here", "" + databaseError);

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public class CustomFollowersAdapter extends ArrayAdapter<UserProfileFollowersHandlers> {

        List<UserProfileFollowersHandlers> userFollowerList;

        public CustomFollowersAdapter(@NonNull Context context, List<UserProfileFollowersHandlers> userProfileFollowersHandlers) {
            super(context, 0, userProfileFollowersHandlers);
            this.userFollowerList = userProfileFollowersHandlers;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View followersView = convertView;
            if (followersView == null) {
                followersView = LayoutInflater.from(FollowerList.this).inflate(R.layout.follower_list_item, parent, false);
            }
            final UserProfileFollowersHandlers userProfileFollowersHandlers = userFollowerList.get(position);
            TextView followerName = followersView.findViewById(R.id.flUserName);
            TextView followerDate = followersView.findViewById(R.id.flDate);
            followerName.setText(userProfileFollowersHandlers.followerNameUserProfile);
            followerDate.setText(userProfileFollowersHandlers.followerFollowedDate);
            return followersView;


        }
    }
}
