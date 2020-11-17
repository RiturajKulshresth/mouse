package com.example.mouse;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoveThread extends Thread {
    private static final String TAG = "MoveThread";

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
        this.y.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                TextView textView= MoveThread.this.y;
//                textView.setText("Touch");
                String sx= textView.getText().toString();

                MoveThread.this.Stream.write((sx + String.valueOf(event.getAction())).getBytes());
                Log.i(TAG,sx);
                return true;
            }




        });

        this.left_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
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

    public void performClick(){}
    public void run()
    {
        this.Stream.write("Connection Started".getBytes());
    }
}