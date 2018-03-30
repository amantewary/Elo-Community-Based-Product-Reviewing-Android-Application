package group.hashtag.projectelo.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import group.hashtag.projectelo.R;
import ru.whalemare.sheetmenu.SheetMenu;

public class ReviewPhotoSelection extends AppCompatActivity {

    private ImageView ReviewPic;
    private File file;
    private Uri uri;
    private Intent CameraIntent, GalleryIntent, CropIntent;
    public static final int PermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_review_layout);

        ReviewPic = (ImageView) findViewById(R.id.new_review_image);
        showMenu();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    // https://github.com/whalemare/sheetmenu
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


}


