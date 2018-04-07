package group.hashtag.projectelo.Activities.OtherUserActivities;

import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import group.hashtag.projectelo.Activities.Wishlist;
import group.hashtag.projectelo.Handlers.WishlistItem;
import group.hashtag.projectelo.R;

public class OtherUserWishlist extends AppCompatActivity {

    Toolbar toolbar;

    TextView title;
    ListView wlListView;
    List<WishlistItem> wishlist;

    DatabaseReference wlItemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_wishlist);

        Intent intent = getIntent();

        String otherUserId = intent.getStringExtra("otherUserId");
        wlItemRef = FirebaseDatabase.getInstance().getReference("User_device").child("Wishlist").child(otherUserId);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");

        wlListView = findViewById(R.id.owlListView);
        wishlist = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
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


    }

    @Override
    protected void onStart() {
        super.onStart();
        wlItemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wishlist.clear();
                for (DataSnapshot wlSnapshot : dataSnapshot.getChildren()) {
                    WishlistItem wlItem = wlSnapshot.getValue(WishlistItem.class);
                    wishlist.add(wlItem);
                }
                OtherUserWishlistAdapter wlAdapter = new OtherUserWishlistAdapter(getApplicationContext(), wishlist);
                wlListView.setAdapter(wlAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Wishlist.class.getCanonicalName(), "Failed to read value.", databaseError.toException());
            }
        });
    }

    public class OtherUserWishlistAdapter extends ArrayAdapter<WishlistItem> {

        private Context context;
        private List<WishlistItem> wishlist;


        public OtherUserWishlistAdapter(Context context, List<WishlistItem> wishlist) {
            super(context, R.layout.wishlist_item, wishlist);
            this.context = context;
            this.wishlist = wishlist;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View followersView = convertView;
            if (followersView == null) {
                followersView = LayoutInflater.from(OtherUserWishlist.this).inflate(R.layout.other_wishlist_item, parent, false);
            }
            final WishlistItem wishlistItem = wishlist.get(position);
            TextView wishlistItemName = followersView.findViewById(R.id.owlItemName);
            TextView wishListCategory = followersView.findViewById(R.id.owlItemCat);
            wishListCategory.setText(wishlistItem.getDeviceCategory());
            wishlistItemName.setText(wishlistItem.getDeviceName());
            return followersView;
        }
    }

}
