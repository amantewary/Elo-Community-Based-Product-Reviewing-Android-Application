package group.hashtag.projectelo.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import group.hashtag.projectelo.Handlers.UserHandler;
import ru.whalemare.sheetmenu.SheetMenu;
import group.hashtag.projectelo.R;


public class SelectPhotoActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;
    private ImageView imageView;
    private File file;
    private Uri uri;
    private Intent CameraIntent, GalleryIntent, CropIntent;
    public static final int PermissionCode = 1;
    StorageReference storageRef;
    String userId;
    String displayPic;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        Intent intent = getIntent();
        if(intent != null){
            userId = intent.getStringExtra("userId");
            displayPic = intent.getStringExtra("displayPicUri");
        }


        storageRef = storage.getReference();


        imageView = (ImageView) findViewById(R.id.iv);
        Picasso.get().load(displayPic).fit().error(R.drawable.cover).placeholder(R.drawable.male).into(imageView);
        title = findViewById(R.id.title_toolbar);

        Typeface ReemKufi_Regular = Typeface.createFromAsset(getAssets(), "fonts/ReemKufi-Regular.ttf");
        Toolbar toolbar = findViewById(R.id.toolbar);
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

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Permission();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_photo, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_photo)
        {
            showMenu();
        }
        return super.onOptionsItemSelected(item);
    }

   // https://github.com/whalemare/sheetmenu
    private void showMenu() {
        SheetMenu.with(this).setTitle("Select An Option:").setAutoCancel(true).setMenu(R.menu.sheet_menu).setClick(new MenuItem.OnMenuItemClickListener() {
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

    private void  Gallery() {

        GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalleryIntent, "Select Image From Gallery"), 2);
    }

    private  void Camera() {

        CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        file = new File(Environment.getExternalStorageDirectory(), "file" + timeStamp + ".jpg");
        uri = Uri.fromFile(file);
        CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        CameraIntent.putExtra("return-data", true);
        startActivityForResult(CameraIntent, 0);
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK)
        {
            Crop();
        }

        else if (requestCode == 2)
        {
            if(data != null)
            {
                uri = data.getData();
                Crop();
            }
        }

        else if (requestCode == 1)
        {
            if (data != null ) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = bundle.getParcelable("data");
                imageView.setImageBitmap(bitmap);
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                StorageReference reviewImageRef = storageRef.child(userId+".jpg");
                bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datai = baos.toByteArray();

                UploadTask uploadTask = reviewImageRef.putBytes(datai);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        try{
                            Map<String, Object> updatePic = new HashMap<>();
                            updatePic.put("Display_Pic",downloadUrl.toString());
                            mDatabase.child(userId).updateChildren(updatePic);

                        }catch (Exception e){
                            Log.e("StackTrace",""+e);
                        }
                    }
                });

            }
        }

    }
    public void Crop() {

        try {
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri,"image/*");
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

        if(ActivityCompat.shouldShowRequestPermissionRationale(SelectPhotoActivity.this, android.Manifest.permission.CAMERA))
        {

            Toast.makeText(SelectPhotoActivity.this, "Allow Camera Permission to Setup Profile", Toast.LENGTH_LONG).show();
        }
        else{
            ActivityCompat.requestPermissions(SelectPhotoActivity.this, new String[] { android.Manifest.permission.CAMERA}, PermissionCode);
        }
    }
    public void onRequestPermissionsResult(int RC, String per[], int[] PRresult) {

        switch (RC) {

            case PermissionCode:

                if (PRresult.length > 0 && PRresult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SelectPhotoActivity.this,"Permission Granted",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SelectPhotoActivity.this, "Permission Canceled",Toast.LENGTH_LONG).show();
                }
                break;


        }
    }





}


