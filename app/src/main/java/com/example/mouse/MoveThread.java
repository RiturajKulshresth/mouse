package com.example.mouse;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class MoveThread extends Thread {
    private static final String TAG = "MoveThreadcatatatat";

    final CThreadActivity Stream;
    final Button left_button;
    final Button right_button;
    final TextView touchview;
    final TextView y;
//    final String string1;

    public MoveThread(CThreadActivity tmp, TextView tv, Button b1, Button b2, TextView y) {
        this.Stream = tmp;
        this.touchview = tv;
        this.left_button = b1;
        this.right_button = b2;
        this.y=y;
//        this.string1=s1;
        tmp.start();
        this.y.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                TextView textView= MoveThread.this.y;
//                textView.setText("Touch");
                String sx= textView.getText().toString();
                MoveThread.this.Stream.write((sx + String.valueOf(event.getAction())).getBytes());
                Log.i(TAG,sx);
                return true;
            }




        });

//        this.touchview.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
////                if(string1==null)
////                    Log.d(TAG,"nullllll");
//                TextView textView = MoveThread.this.touchview;
////                textView.setText("Touch coordinates : " + textView.getText().toString()+"catacat");
////                Log.d(TAG,textView.getText().toString());
//
//                textView.setText("Touch coordinates : " + String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()) + "::" + String.valueOf(event.getAction()));
////                MoveThread.this.Stream.write(("(" + String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()) + "):" + String.valueOf(event.getAction())).getBytes());
//                return true;
//            }
//        });


        this.left_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MoveThread.this.left_button.getText() != "clicked") {
                    MoveThread.this.Stream.write(new byte[]{108});
                }
            }
        });
        this.left_button.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                byte[] temp = {76};
                if (MoveThread.this.left_button.getText() == "clicked") {
                    MoveThread.this.left_button.setText("left click");
                } else {
                    MoveThread.this.left_button.setText("clicked");
                }
                MoveThread.this.Stream.write(temp);
                return false;
            }
        });
        this.right_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MoveThread.this.right_button.getText() != "clicked") {
                    MoveThread.this.Stream.write(new byte[]{114});
                }
            }
        });
        this.right_button.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                byte[] temp = {82};
                if (MoveThread.this.right_button.getText() == "clicked") {
                    MoveThread.this.right_button.setText("right click");
                } else {
                    MoveThread.this.right_button.setText("clicked");
                }
                MoveThread.this.Stream.write(temp);
                return false;
            }
        });
    }

    public void performClick(){

    }
    public void run() {
        this.Stream.write("Connection Started :)".getBytes());
    }
}