package com.example.mouse;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Set;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    static BluetoothSocket ConnectedSocket = null;
    final int REQUEST_ENABLE_BT = 120;
    BluetoothDevice extcomputer;
    String string1;
    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    Sensor accelerometer;
    TextView xvalue, yvalue;
    float vel_xa,vel_x;
    float vel_ya,vel_y;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        vel_x = (float) (sensorEvent.values[0]*0.2);
        vel_y = (float) (sensorEvent.values[1]*0.2);
        vel_xa= vel_x+(float) (sensorEvent.values[0]*0.2);
        vel_ya= vel_y+(float) (sensorEvent.values[1]*0.2);

        if((abs(x)>0.3)||(abs(y)>0.3))
        {
            yvalue.setText("(" + (vel_xa) +"x" + (vel_ya) +")"+":");
            Log.d(TAG, "X : " + vel_x + " Y : " + vel_y);
        }
        else
        {
            yvalue.setText("(" + 0.0000+"x" + 0.0000+")"+":");
            Log.d(TAG, "X : " + 0.0000 + " Y : " + 0.0000);
        }

//
//        Log.d(TAG, "X : " + sensorEvent.values[0] + " Y : " + sensorEvent.values[1]);
//        String cart=(TextView) findViewById(R.id.touch_view);
//        cView.setText("X: " + sensorEvent.values[0]);

        string1 = "(" + sensorEvent.values[0]+"x" + sensorEvent.values[1]+")"+":";
//        String string1="cat";
//        Log.d(TAG, yvalue.getText().toString());
//        Log.d(TAG, )
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String string1="cat";
        Toast.makeText(getApplicationContext(), "Check Bluetooth", Toast.LENGTH_LONG).show();
        setContentView((int) R.layout.activity_main);
        yvalue=(TextView) findViewById(R.id.yvalue);
        BluetoothAdapter btadapter = BluetoothAdapter.getDefaultAdapter();
        if (btadapter == null) {
            System.out.println("****no bluetooth adapter*****");
            Log.i("BlumouseActivity", "****no bluetooth adapter*****");
        } else {
            System.out.println("****THERE IS A bluetooth adapter*****");
            Log.i("BlumouseActivity", "****THERE IS A bluetooth adapter*****");
            if (!btadapter.isEnabled()) {
                Log.i("BlumouseActivity", "Bluetooth not enabled");
                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 120);
            }
        }
        Set<BluetoothDevice> pairedDevices = btadapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            Log.i("aaa", "there are paired devices!!");
            for (BluetoothDevice device : pairedDevices) {
                this.extcomputer = device;
                String addr = device.getAddress();
                Log.i("aaa", "paired devices address: " + addr);
            }
        }

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG, "abrakadabra"+ string1);

        new ThreadActivity(this.extcomputer, (TextView) findViewById(R.id.touch_view), (Button) findViewById(R.id.leftbutton), (Button) findViewById(R.id.rightbutton),(TextView) findViewById(R.id.yvalue)).start();
        Log.i(TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+yvalue.getText().toString());
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestcode, int resultcode, Intent intent) {
        super.onActivityResult(requestcode, resultcode, intent);
        if (requestcode == 120) {
            if (resultcode == -1) {
                Log.i("onActivityResult", "Result ok :)");
            } else if (resultcode == 0) {
                Log.i("onActivityResult", "Result cancelled :(");
            }
        }
    }
}