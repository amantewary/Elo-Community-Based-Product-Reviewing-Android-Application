package group.hashtag.projectelo.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;

import group.hashtag.projectelo.R;


public class GalCam extends AppCompatActivity {

    ImageView i1;
    Button Camtbtn;
    Button Galbtn;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    boolean camera = false;
    boolean gallery = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i1 = (ImageView) findViewById(R.id.imageView);

        Galbtn = (Button) findViewById(R.id.og);
        Camtbtn = (Button) findViewById(R.id.oc);

        Camtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, 0);
                camera = true;

            }
        });


    }


    public void open_gallery(View v) {
        Crop.pickImage(this);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        System.out.println("enterferfe4rdtgfe4rttttttttttttttttttereddddddddddddd");

        if(camera==true)
        {
            initiateCameraProperties(data);
            camera=false;
        }
        else
        {
            if (requestCode == Crop.REQUEST_PICK)
            {
                System.out.println("ffffffffffsssssssssfssfEEEEEEEEEEEEEEEEEEEEEE"+Crop.REQUEST_PICK+","+requestCode);
                Uri source_uri = data.getData();
                Uri destination_uri = Uri.fromFile(new File(getCacheDir(), "cropped"));
                Crop.of(source_uri, destination_uri).asSquare().start(this);
                i1.setImageURI(Crop.getOutput(data));
            }
            else if (requestCode == Crop.REQUEST_CROP)
            {
                handle_crop(resultCode, data);
            }

        }






//            System.out.println("HGHHJGJHGVJK,HGKJ,GK,JGKGJHJGJHGMNJHGMNJHGMNHJGNMHGBNHJGH"+RESULT_OK+","+requestCode);
//            if (resultCode == RESULT_OK)
//            {
//                System.out.println("SEEEEEEEEEEEEEEEEEEEEEE"+RESULT_OK+","+requestCode);
//                System.out.println("SEEEEEEEEEEEEEEEEEEEEEE"+RESULT_OK+","+requestCode);
//
//                System.out.println("outttttttttttttttttttttt"+Crop.REQUEST_PICK+","+requestCode);
//                if (requestCode == Crop.REQUEST_PICK)
//                {
//                    System.out.println("ffffffffffsssssssssfssfEEEEEEEEEEEEEEEEEEEEEE"+Crop.REQUEST_PICK+","+requestCode);
//                    Uri source_uri = data.getData();
//                    Uri destination_uri = Uri.fromFile(new File(getCacheDir(), "cropped"));
//                    Crop.of(source_uri, destination_uri).asSquare().start(this);
//                    i1.setImageURI(Crop.getOutput(data));
//                }
//                else if (requestCode == Crop.REQUEST_CROP)
//                {
//                    handle_crop(resultCode, data);
//                }


//            }

    }



    private void initiateCameraProperties(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        System.out.println("enterferfe4rdtgfe4rttttttttttttttttttereddddddddddddd");
        i1.setImageBitmap(bitmap);
    }

    public void handle_crop(int code, Intent result) {
        if (code == RESULT_OK) {
            i1.setImageURI(Crop.getOutput(result));
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }


        }

        if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            Log.v("","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("","Permission is granted");
                return true;
            } else {

                Log.v("","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("","Permission is granted");
            return true;
        }
    }
}
