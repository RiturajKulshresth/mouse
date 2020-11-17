package com.example.mouse;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class CThreadActivity extends Thread
{
    private static final String TAG = "CThreadActivity";
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final BluetoothSocket mmSocket;

    public CThreadActivity(BluetoothSocket socket)
    {
        this.mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try
        {

            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        }
        catch (IOException e){}
        this.mmInStream = tmpIn;
        this.mmOutStream = tmpOut;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                int read = this.mmInStream.read(new byte[1024]);
                Log.i(TAG, "Received bytes");
            } catch (IOException e)
            {
                return;
            }
        }
    }

    public void write(byte[] bytes)
    {
        try
        {
            this.mmOutStream.write(bytes);
        }
        catch (IOException e) {}
    }

    public void cancel()
    {
        try
        {
            this.mmSocket.close();
        }
        catch (IOException e){}
    }
}