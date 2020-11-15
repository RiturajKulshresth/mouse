package com.example.mouse;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
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
import java.io.InputStream;
import java.io.OutputStream;


public class CThreadActivity extends Thread {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final BluetoothSocket mmSocket;

    public CThreadActivity(BluetoothSocket socket) {
        this.mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }
        this.mmInStream = tmpIn;
        this.mmOutStream = tmpOut;
    }

    public void run() {
        while (true) {
            try {
                int read = this.mmInStream.read(new byte[1024]);
                Log.i("RECIEVED BYTES", ":)");
            } catch (IOException e) {
                return;
            }
        }
    }

    public void write(byte[] bytes) {
        try {
            this.mmOutStream.write(bytes);
        } catch (IOException e) {
        }
    }

    public void cancel() {
        try {
            this.mmSocket.close();
        } catch (IOException e) {
        }
    }
}