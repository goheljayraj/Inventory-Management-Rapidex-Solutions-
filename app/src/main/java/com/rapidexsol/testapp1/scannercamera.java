package com.rapidexsol.testapp1;

import static android.content.ContentValues.TAG;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.dm7.barcodescanner.core.CameraPreview;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannercamera extends AppCompatActivity implements ZXingScannerView.ResultHandler, SensorEventListener {

    ZXingScannerView ScannerView;
    DataBase dataBase = new DataBase(scannercamera.this);
    Inventory invent;
    float x=0,y=0,z=0;
    Bitmap bitmap;
    View view;


    Camera mCamera;
    CameraPreview mPreview;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
//        Log.d("invent2","HELLO");
        System.out.println("HELLO");


        ////////////////////ACCELEROMETER
        SensorManager sensormanager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if(sensormanager!=null){
            Sensor accelerosensor = sensormanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            if(accelerosensor != null){
                sensormanager.registerListener(this,accelerosensor,999999999);
            }
        }
        else {
            Toast.makeText(scannercamera.this, "Sensor service not available", Toast.LENGTH_SHORT).show();
        }
        /////////////////////////////////////////////////////////

        //Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleResult(Result rawResult) {

        String barres = rawResult.getText();//getText().toString();
        if(x>0.06 || y>0.06 || z>0.06) {
            Toast.makeText(getApplicationContext(), barres, Toast.LENGTH_SHORT).show();

            try {
                invent = new Inventory(-1, "", barres, 1, 1);
                Toast.makeText(scannercamera.this, "Item Added", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(scannercamera.this, "Error Adding Item", Toast.LENGTH_SHORT).show();
                invent = new Inventory(-1, "error", "error", 0, 0);

            }


            boolean success = dataBase.updateOne(invent);
            if (success == true) {
//                View rootView = findViewById(android.R.id.content).getRootView();
//                rootView.setDrawingCacheEnabled(true);
//                Bitmap bitmap = rootView.getDrawingCache();

//Inetnt data;
//            Bitmap image = (Bitmap) data.getExtras().get("data");


//
//            bitmap = getScreenShotFromView(new SurfaceView(this));
//                if (bitmap != null) {
//
//                    Toast.makeText(scannercamera.this, "Got the image", Toast.LENGTH_SHORT).show();
//                    saveToInternalStorage(bitmap);
//                }
                Toast.makeText(scannercamera.this, "true", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(scannercamera.this, "false", Toast.LENGTH_SHORT).show();
            }
        }

        ScannerView.resumeCameraPreview(this);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
//        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        // Create imageDir
        File logDir = new File (directory, "Rapidex Solutions IMAGES"); //Creates a new folder in DOWNLOAD directory
        logDir.mkdirs();
        File mypath = new File(logDir, "FILENAME.jpg");
//        File mypath=new File(directory,"jayraj1.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }



    private Bitmap getScreenShotFromView(SurfaceView v) {
        Bitmap screenshot = (Bitmap)null;

        try {
            screenshot = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(screenshot);
            v.draw(canvas);
        } catch (Exception var4) {
            Log.e("GFG", "Failed to capture screenshot because:" + var4.getMessage());
        }

        return screenshot;
    }


    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        ScannerView.startCamera();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
            // ((TextView)findViewById(R.id.accelviewid)).setText("X: "+ sensorEvent.values[0]+", Y: "+sensorEvent.values[2]+", Z: "+sensorEvent.values[2]);
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];



        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }




}