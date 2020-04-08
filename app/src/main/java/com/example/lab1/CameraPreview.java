
package com.example.lab1;

import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Camera camera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        this.holder = getHolder();
        this.holder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (this.camera == null) {
                this.camera.setPreviewDisplay(holder);
                this.camera.startPreview();
            }
        } catch (IOException e) {
            Log.i("Camera Preview", "Error camera preview: " + e.getMessage());
        }
    }

    public void refreshCamera(Camera camera) {
        if (this.holder.getSurface() == null) {
            return;
        }

        try {
            this.camera.stopPreview();
        } catch (Exception e) {
        }

        setCamera(camera);
        try {
            this.camera.setPreviewDisplay(this.holder);
            this.camera.startPreview();
        } catch (Exception e) {
            Log.d("Camera Preview", "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        refreshCamera(this.camera);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
} 