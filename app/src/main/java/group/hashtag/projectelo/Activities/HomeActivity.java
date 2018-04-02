package group.hashtag.projectelo.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import group.hashtag.projectelo.Handlers.FeaturedContentHandler;
import group.hashtag.projectelo.Handlers.ReviewHandler;
import group.hashtag.projectelo.R;
import group.hashtag.projectelo.SettingsActivity;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener , View.OnClickListener {


    ListView listView;
    TextView title;
    CustomAdapter arrayAdapter;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    MaterialSearchView categories;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    DatabaseReference userRef;
    ReviewHandler reviewHandler;

    private static final String SHOWCASE_ID = "element_display";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    boolean firstRun;
    boolean clicked;

    List<ReviewHandler> reviewHandlerList;

    TextView featuredTitle;
    FeaturedContentHandler featuredContentHandler;
    ImageView imageViewTitle;
    FloatingActionButton fab;
    TextView navUsername;
    RoundCornerProgressBar navProgress;
    Map<String, Object> mapUser;

    String FeaturedTitleString;
    String FeaturedContentString;
    String FeaturedWriter;

    CircleImageView navDisplayPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        LinearLayout linearLayout = findViewById(R.id.linear_layout_featured);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_reviews);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        reviewHandler = new ReviewHandler();
        featuredContentHandler = new FeaturedContentHandler();

        categories = findViewById(R.id.search_catogories);
        title = findViewById(R.id.title_toolbar);
        final Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        featuredTitle = findViewById(R.id.featured_textView_title);
        reviewHandlerList = new ArrayList<ReviewHandler>();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
        listView = findViewById(R.id.list_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        arrayAdapter = new CustomAdapter(this, reviewHandlerList);

        listView.canScrollVertically(0);

        listView.setAdapter(arrayAdapter);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("newReview");
        mDatabase2 = database.getReference("Feature_article");
        fetchFeaturedData(featuredTitle, imageViewTitle, mDatabase2);

//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        account = GoogleSignIn.getLastSignedInAccount(this);

        prefs = HomeActivity.this.getSharedPreferences("group.hashtag.projectelo.Activities.HomeActivity",0);
        editor= prefs.edit();
        firstRun = prefs.getBoolean("firstRun", true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

//        firstRun=true;
        if(firstRun)
        {
            final Toast toast = Toast.makeText(getApplicationContext(), "Click on the plus button for user manual!", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000000);



        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reviewHandlerList.clear();
                for (DataSnapshot dsnp : dataSnapshot.getChildren()) {

                    Map<String, Object> map = (Map<String, Object>) dsnp.getValue();
                    reviewHandler = new ReviewHandler(map.get("category").toString(), map.get("device").toString(), map.get("reviewDescription").toString(), map.get("reviewId").toString(), map.get("reviewTitle").toString(), map.get("userId").toString());
//                    Log.e("Here", "" + map);
                    reviewHandlerList.add(reviewHandler);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FeaturedArticle.class);
                Bundle b = new Bundle();
                b.putString("FeaturedTitle", FeaturedTitleString);
                b.putString("FeaturedContent", FeaturedContentString);
                b.putString("FeaturedWriter", FeaturedWriter);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);


        //Todo: Populate suggestions from db remaining
        categories.setSuggestions(getResources().getStringArray(R.array.device_categories));
        categories.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        categories.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                categories.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                categories.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);

            }
        });
        navProgress = header.findViewById(R.id.nav_progress);
        navUsername = header.findViewById(R.id.nav_username);
        navDisplayPic = header.findViewById(R.id.nav_profileimg);
        userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapUser = (Map<String, Object>) dataSnapshot.getValue();
                String userName = mapUser.get("name").toString();
                String userLike = mapUser.get("likes").toString();
                String userImage = mapUser.get("Display_Pic").toString();
                Log.e("Here", "Current UserName => " + userName);
                navUsername.setText(userName);
                navProgress.setProgress(Integer.parseInt(userLike));
                loadDisplayPics(userImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });
        clicked = false;

    }


    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.fab)
        {
            if(!clicked) {
                clicked = true;
                if (firstRun) {
                    MaterialShowcaseView.resetAll(getApplicationContext());
                    presentShowcaseSequence();
                    Log.e("onCreate: ", " First time");
                    editor.putBoolean("firstRun", false); // It is no longer the first run
                    editor.apply();
                }else{
                    startActivity(new Intent(getApplicationContext(), NewReviewActivity.class));
                }
            }else {
                    startActivity(new Intent(getApplicationContext(), NewReviewActivity.class));
                    Log.i("onCreate: ", "Not First time");
                }
        }
    }

    private void presentShowcaseSequence()
    {
        // Items added to showcase
        // 1. Navigation view
        // 2. FAB
        // 3. List View
        // 4. Settings
        // 5. Search

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ShowcaseConfig config = new ShowcaseConfig();

        // one and a half second between each showcase view
        config.setDelay(1000);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);


        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener()
        {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position)
            {
                if(position==1)
                {
                    drawer.openDrawer(GravityCompat.START);
                }
                else if(position==2)
                {
                    if (drawer.isDrawerOpen(GravityCompat.START))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
                else
                {
                    if (drawer.isDrawerOpen(GravityCompat.START))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                    }

                }

            }
        });

        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.linear_layout_featured))
                        .setDismissText("GOT IT")
                        .setContentText(" \n Checkout the highlighted reviews here!  \n \n")
                        .withRectangleShape()
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.drawer_layout))
                        .setMaskColour(R.color.colorPrimary)
                        .setDismissText("GOT IT")
                        .setMaskColour(R.color.colorBlue)
                        .setContentText("\n Would you like to update your details?Click on the User Profile option!\n\nTo change your app settings select the Settings options.\n")
                        .withoutShape()
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(findViewById(R.id.toolbar))
                        .setDismissText("GOT IT")
                        .setContentText(" \n Looking for something? Type your key words here for the search results to appear below! \n")
                        .withRectangleShape()
                        .build()
        );



        sequence.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (categories.isSearchOpen()) {
            categories.closeSearch();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        categories.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user_profile) {
            startActivity(new Intent(HomeActivity.this, UserProfile.class));
        } else if (id == R.id.nav_licence) {
            startActivity(new Intent(getApplicationContext(), OpenSourceActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        } else if (id == R.id.nav_logout) {
            showDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(HomeActivity.this, Register_Activity.class));
                finish();
            }
        }
    };

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");

        String positiveText = getString(android.R.string.yes);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();

                    }
                });

        String negativeText = getString(android.R.string.no);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing here
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    public void onRefresh() {
        //Todo: add a code to actually refresh reviews
        Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public class CustomAdapter extends ArrayAdapter<ReviewHandler> implements Filterable {

        List<ReviewHandler> reviewHandlerList;
        List<ReviewHandler> searchDevices;

        public CustomAdapter(@NonNull Context context, List<ReviewHandler> reviewHandlerList) {
            super(context, 0, reviewHandlerList);
            this.reviewHandlerList = reviewHandlerList;
            this.searchDevices = reviewHandlerList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View reviewtitles = convertView;
            if (reviewtitles == null) {
                reviewtitles = LayoutInflater.from(HomeActivity.this).inflate(R.layout.list_view_items, parent, false);
            }
            final ReviewHandler reviewHandler = searchDevices.get(position);
            reviewtitles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ProductReview.class);
                    Bundle b = new Bundle();
                    b.putString("reviewAuthor", reviewHandler.userId);
                    b.putString("reviewTitle", reviewHandler.reviewTitle);
                    b.putString("reviewContent", reviewHandler.reviewDescription);
                    b.putString("category", reviewHandler.category);
                    b.putString("reviewId",reviewHandler.reviewId);
                    intent.putExtras(b);
                    startActivity(intent);

                }
            });

            TextView reviewTitle = reviewtitles.findViewById(R.id.text_headline);
            reviewTitle.setText(reviewHandler.reviewTitle);
            return reviewtitles;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    searchDevices = (List<ReviewHandler>) results.values;
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    String charString = constraint.toString();
                    if (charString.isEmpty()) {
                        searchDevices = reviewHandlerList;
                    } else {
                        List<ReviewHandler> filteredList = new ArrayList<ReviewHandler>();
                        for (ReviewHandler reviewHandler : reviewHandlerList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (reviewHandler.getCategorys().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(reviewHandler);
                            }
                        }

                        searchDevices = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = searchDevices;
                    return filterResults;
                }
            };
        }

        @Override
        public int getCount() {
            if (searchDevices == null) {
                return 0;
            } else {
                return searchDevices.size();
            }
        }
    }


    public void fetchFeaturedData(final TextView textTitle, ImageView imageFeaturedPic, DatabaseReference mDatabase2) {
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FeaturedContentHandler featuredContentHandler = dataSnapshot.getValue(FeaturedContentHandler.class);
                FeaturedTitleString = featuredContentHandler.getArticle_titless();
                FeaturedContentString = featuredContentHandler.getArticle_contentss();
                FeaturedWriter = featuredContentHandler.getExternal_Userss();
                textTitle.setText(featuredContentHandler.getArticle_titless());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });

    }

    public void loadDisplayPics(String url){
        Picasso.get().load(url).fit().error(R.drawable.cover).placeholder(R.drawable.male).into(navDisplayPic);

    }
}

