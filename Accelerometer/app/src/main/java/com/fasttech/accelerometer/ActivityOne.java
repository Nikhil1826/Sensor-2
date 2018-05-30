package com.fasttech.accelerometer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityOne extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    TextView tn;
    Button btn;
    SensorManager sensorManager;
    Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        tn = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float values[] = sensorEvent.values;
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float cal = ((x * x) + (y * y) + (z * z)) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        if (cal > 10) {
            tn.setText("Shake Detected");
            String phone = "+91 9914005560";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Please Grant Permissions", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(intent);
                sensorManager.unregisterListener(this);
            }
        }else{
            tn.setText(x+","+y+","+z);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View view) {
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
}
