package group.hashtag.projectelo.Activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import group.hashtag.projectelo.R;

/**
 * Created by amant on 17-03-2018.
 */

public class WishlistAdapter extends ArrayAdapter<WishlistItem>{

    private Activity context;
    private List<WishlistItem> wishlist;


    public WishlistAdapter(Activity context, List<WishlistItem> wishlist) {
        super(context, R.layout.wishlist_item, wishlist);
        this.context = context;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View wishlistItem = inflater.inflate(R.layout.wishlist_item, null, true);

        TextView wlDeviceName = wishlistItem.findViewById(R.id.wlItemName);
        TextView wlCategoryName = wishlistItem.findViewById(R.id.wlItemCat);

        WishlistItem wlItem = wishlist.get(position);

        wlDeviceName.setText(wlItem.getDeviceName());
        wlCategoryName.setText(wlItem.getDeviceCategory());

        return wishlistItem;

    }
}
