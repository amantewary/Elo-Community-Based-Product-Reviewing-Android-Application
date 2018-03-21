package group.hashtag.projectelo.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import group.hashtag.projectelo.R;

public class FollowerList extends AppCompatActivity {

    TextView flName;
    TextView flDate;
    String followerId;
    String followerDate;
    String followerName;
    Map<String, Object> mapUser;
    Map<String, Object> mapFollower;
    DatabaseReference userRef;
    DatabaseReference followRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.follower_list);
        followRef = FirebaseDatabase.getInstance().getReference("follow");
        followRef.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot followers : dataSnapshot.getChildren()){
                    followerId = followers.getKey();
                    followerDate = followers.getValue().toString();
                    userRef = FirebaseDatabase.getInstance().getReference("users");
                    userRef.child(followerId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mapUser = (Map<String, Object>) dataSnapshot.getValue();
                            Log.e("Here", "" + mapUser);
                            followerName = mapUser.get("name").toString();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("Here", ""+databaseError);

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
