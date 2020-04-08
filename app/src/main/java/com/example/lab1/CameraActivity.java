package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private Camera camera;
    private CameraPreview preview;
    private Camera.PictureCallback pictureCallback;
    private LinearLayout cameraPreview;
    private Integer cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private boolean cameraFront = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.requestPermissions();

        this.camera = Camera.open(this.cameraId);
        this.camera.setDisplayOrientation(90);
        cameraPreview = (LinearLayout) findViewById(R.id.camera_preview);
        this.preview = new CameraPreview(getApplicationContext(), this.camera);
        cameraPreview.addView(this.preview);
        this.camera.startPreview();
    }

    public void openCamera() {
        if (this.camera == null) {
            this.camera = Camera.open(this.cameraId);
        }
        this.camera.setDisplayOrientation(90);
        this.pictureCallback = this.getPictureCallback();
        this.preview.refreshCamera(this.camera);
    }

    public void onResume() {

        super.onResume();
        this.openCamera();
    }

    public void takePicture(View view) {
        this.camera.takePicture(null, null, this.pictureCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Camera Activity", "onstart");
    }

    private void releaseCamera() {
        if (this.camera != null) {
            this.camera.stopPreview();
            this.camera.setPreviewCallback(null);
            this.camera.release();
            this.camera = null;
        }
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (camera != null) {
                    camera.stopPreview();
                    camera.startPreview();
//                    camera.setPreviewCallback(null);
//                    camera.release();
//                    camera = null;
                }
                Log.i("Picture Callback", "callback");
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "androidlab_photo.jpeg");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    Log.i("Picture Callback", e.getMessage());
                }
            }
        };
        return picture;
    }

    @SuppressLint("NewApi")
    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 103);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
    }
}