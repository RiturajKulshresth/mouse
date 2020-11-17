package com.example.mouse;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class ThreadActivity extends Thread
{

    private static final String TAG = "Thread";
    private final UUID MY_UUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee");
    final Button left_button;
    private final BluetoothDevice mmDevice;
    private final BluetoothSocket mmSocket;
    final Button right_button;
    final TextView touchview;
    final TextView y;

    public ThreadActivity(BluetoothDevice device, TextView tv, Button b1, Button b2, TextView y)
    {
        BluetoothSocket tmp = null;
        this.mmDevice = device;
        this.touchview = tv;
        this.left_button = b1;
        this.right_button = b2;
        this.y=y;

        BluetoothDevice hxm = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
        try
        {
            Method m = hxm.getClass().getMethod("createRfcommSocket", new Class[]{Integer.TYPE});
            Log.i(TAG, "This try happened");
            tmp = (BluetoothSocket) m.invoke(hxm, new Object[]{1});
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2)
        {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3)
        {
            e3.printStackTrace();
        }
        catch (SecurityException e4)
        {
            e4.printStackTrace();
        }
        catch (NoSuchMethodException e5)
        {
            e5.printStackTrace();
        }
        Log.i("Socket operation", "Socket Created");
        Log.i(TAG, "Socket Info "+tmp.toString());
        this.mmSocket = tmp;
    }

    public void run()
    {
        Log.i(TAG, "Socket operation successfully started");
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        try
        {
            this.mmSocket.connect();
            Log.i("socket operation", "trying to connect");
            Log.i("socket operation", "completed connect thread");
            new MoveThread(new CThreadActivity(this.mmSocket), this.touchview, this.left_button, this.right_button, this.y).start();
        } catch (IOException connectException) {
            Log.i("socket operation", "caught ioexception");
            Log.i("Message", connectException.getMessage());
            Log.i("LMessage", connectException.getLocalizedMessage());
            try
            {
                this.mmSocket.close();
            }
            catch (IOException e){}
        }
    }

    public void cancel()
    {
        try
        {
            Log.i("socket operation", "closing socket");
            this.mmSocket.close();
        }
        catch (IOException e){}
    }

    public BluetoothSocket GiveSocket()
    {
        return this.mmSocket;
    }
}