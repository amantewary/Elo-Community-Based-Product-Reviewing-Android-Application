package group.hashtag.projectelo.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import group.hashtag.projectelo.Handlers.WishlistItem;
import group.hashtag.projectelo.R;

/**
 * Created by amant on 16/03/18.
 */

public class Wishlist extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton addDevice;
    TextView title;
    ListView wlListView;
    List<WishlistItem> wishlist;

    DatabaseReference wlItemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.wishlist_main);

        wlItemRef = FirebaseDatabase.getInstance().getReference("User_device").child("Wishlist").child(auth.getUid());
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        wlListView = findViewById(R.id.wlListView);
        wishlist = new ArrayList<>();
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

    @Override
    protected void onStart() {
        super.onStart();
        wlItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wishlist.clear();
                for(DataSnapshot wlSnapshot : dataSnapshot.getChildren()){
                    WishlistItem wlItem = wlSnapshot.getValue(WishlistItem.class);
                    wishlist.add(wlItem);
                }
                WishlistAdapter wlAdapter= new WishlistAdapter(Wishlist.this,wishlist);
                wlListView.setAdapter(wlAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Wishlist.class.getCanonicalName(), "Failed to read value.", databaseError.toException());
            }
        });
    }

}
