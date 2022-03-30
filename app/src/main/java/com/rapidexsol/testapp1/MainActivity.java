package com.rapidexsol.testapp1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

        private Button button,buttonadditems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////////CAMERA PERMISSION
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                    50); }

        ////////////////////STORAGE PERMISSION
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0); }

        ////////////////////ACCELEROMETER
        SensorManager sensormanager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if(sensormanager!=null){
            Sensor accelerosensor = sensormanager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            if(accelerosensor != null){
                sensormanager.registerListener(this,accelerosensor,999999999);
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Sensor service not available", Toast.LENGTH_SHORT).show();
        }




        button = (Button) findViewById(R.id.buttonpress);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openscannercamera();
            }
        });

        buttonadditems = (Button) findViewById(R.id.buttonadditems);
        buttonadditems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openadditemsactivity();
            }
        });
    }

    private void openadditemsactivity() {
        Intent intent1 = new Intent(this,additemsactivity.class);
        startActivity(intent1);
    }

    private void openscannercamera() {
        Intent intent2 = new Intent(this,scannercamera.class);
        startActivity(intent2);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
           // ((TextView)findViewById(R.id.accelviewid)).setText("X: "+ sensorEvent.values[0]+", Y: "+sensorEvent.values[2]+", Z: "+sensorEvent.values[2]);
            if(sensorEvent.values[0] > 0.06 || sensorEvent.values[1] > 0.06 || sensorEvent.values[2] > 0.06) {
                Toast.makeText(MainActivity.this, "ture", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}