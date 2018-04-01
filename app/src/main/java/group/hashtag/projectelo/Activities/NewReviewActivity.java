package group.hashtag.projectelo.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import group.hashtag.projectelo.Handlers.NewReviewHandler;
import group.hashtag.projectelo.R;
import ru.whalemare.sheetmenu.SheetMenu;

/**
 * Created by nikhilkamath on 01/03/18.
 */

public class NewReviewActivity extends AppCompatActivity {
    Toolbar toolbar;
    Spinner category, device;
    EditText newReviewTitle;
    EditText newReviewDescription;
    TextView title;
    DatabaseReference mDatabase2;
    DatabaseReference mDatabase1;
    DatabaseReference reviewDatabase;

    Map<String, Object> mapCategories;
    Map<String, Object> mapDevice;
    List<String> listCategories;
    List<String> listDevices;
    List<Map<String, Object>> listMapCategories;
    List<Map<String, Object>> listMapDevices;
    ArrayAdapter<String> categories;
    ArrayAdapter<String> devices;

    private ImageView ReviewPic;
    private File file;
    private Uri uri;
    private Intent CameraIntent, GalleryIntent, CropIntent;
    public static final int PermissionCode = 1;

    //Todo: validate spinner before adding new review
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_review_layout);

        ReviewPic = (ImageView) findViewById(R.id.new_review_image);
        Permission();


        reviewDatabase = FirebaseDatabase.getInstance().getReference("newReview");
        title = findViewById(R.id.title_toolbar);
        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");


        listCategories = new ArrayList<>();
        listMapCategories = new ArrayList<>();

        listDevices = new ArrayList<>();


        listDevices = new ArrayList<>();
        listMapDevices = new ArrayList<>();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase2 = database.getReference("Device_Category");
        mDatabase1 = database.getReference("Device");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title.setTypeface(ReemKufi_Regular);

        fetchCategories(mDatabase2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        category = findViewById(R.id.spinner_device_category);
        device = findViewById(R.id.spinner_product_name);
        newReviewTitle = findViewById(R.id.new_review_title);
        newReviewDescription = findViewById(R.id.new_review_description);

//        listDevices = new ArrayList<String>(mapDevice.keySet());

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
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        findViewById(R.id.new_review_image).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view) {

                showMenu();


            }
        });
    }

    private void showMenu() {
        SheetMenu.with(this)
                .setTitle("Select An Option:").setMenu(R.menu.sheet_menu).setClick(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_cam) {

                    Camera();


                } else if(item.getItemId() == R.id.action_gal) {

                    Gallery();

                }
                return false;
            }
        }).show();
    }
    private void  Gallery()
    {

        GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalleryIntent, "Select Image From Gallery"), 2);
    }

    private  void Camera()
    {

        CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        file = new File(Environment.getExternalStorageDirectory(), "file" + timeStamp + ".jpg");
        uri = Uri.fromFile(file);
        CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        CameraIntent.putExtra("return-data", true);
        startActivityForResult(CameraIntent, 0);
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK) {
            Crop();
        }

        else if (requestCode == 2) {
            if(data != null) {
                uri = data.getData();
                Crop();
            }
        }

        else if (requestCode == 1) {
            if (data != null ) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                ReviewPic.setImageBitmap(bitmap);
            }
        }

    }
    public void Crop() {

        try {
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri,"image");
            CropIntent.putExtra("crop", true);
            CropIntent.putExtra("OutputX", 256);
            CropIntent.putExtra("OutputY", 256);
            CropIntent.putExtra("aspectX",1);
            CropIntent.putExtra("aspectY",1);
            CropIntent.putExtra("scale", true);
            CropIntent.putExtra("return-data",true);

            startActivityForResult(CropIntent, PermissionCode);


        }catch (ActivityNotFoundException e) {

        }

    }

    public void Permission() {

        if(ActivityCompat.shouldShowRequestPermissionRationale(NewReviewActivity.this, android.Manifest.permission.CAMERA))
        {

            Toast.makeText(NewReviewActivity.this, "Allow Camera Permission to Add photos", Toast.LENGTH_LONG).show();
        }
        else{
            ActivityCompat.requestPermissions(NewReviewActivity.this, new String[] { android.Manifest.permission.CAMERA}, PermissionCode);
        }
    }
    public void onRequestPermissionsResult(int RC, String per[], int[] PRresult) {

        switch (RC) {

            case PermissionCode:

                if (PRresult.length > 0 && PRresult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(NewReviewActivity.this,"Permission Granted",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(NewReviewActivity.this, "Permission Canceled",Toast.LENGTH_LONG).show();
                }
                break;


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_post) {
            addReview();
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        return super.onOptionsItemSelected(item);
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

    public void fetchDevice(Query query) {
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDevices.add(0,"Select Device");
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

    public void addReview() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        String userId = auth.getUid();
        String reviewTitle = newReviewTitle.getText().toString().trim();
        String reviewDescription = newReviewDescription.getText().toString().trim();
        String deviceCategory = category.getSelectedItem().toString();
        String deviceName = device.getSelectedItem().toString();

        if (!TextUtils.isEmpty(reviewTitle) && !TextUtils.isEmpty(reviewDescription)) {
            String id = reviewDatabase.push().getKey();
            NewReviewHandler newReview = new NewReviewHandler(userId, id, reviewTitle, reviewDescription, deviceName, deviceCategory);
            reviewDatabase.child(id).setValue(newReview);
            Toast.makeText(this, "New Review Added", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (reviewTitle.matches("")) {
                newReviewTitle.setError("Title cannot be empty");
                return;
            }
            if (reviewDescription.matches("")) {
                newReviewDescription.setError("Description cannot be empty");
                return;
            }
        }
    }
}
