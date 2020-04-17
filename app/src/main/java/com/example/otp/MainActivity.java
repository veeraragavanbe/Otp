    package com.example.otp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


    public class MainActivity extends AppCompatActivity {


    public static final int REQUEST_ID_MULTIPLE_PERMISSION = 1;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Sendmsg.class);
                startActivity(in);
            }
        });


        if(checkAndRequestPermissions())
        {
            //Wait
        }

        }

        private BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equalsIgnoreCase("otp"))
                {
                    final String message = intent.getStringExtra("message");
                    TextView tv = findViewById(R.id.textview);
                    tv.setText(message);
                }
            }
        };

        private boolean checkAndRequestPermissions() {

            int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
            int readSMS = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS);

            List<String> listPermissionNeeded = new ArrayList<>();

            if(receiveSMS != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionNeeded.add(Manifest.permission.RECEIVE_MMS);
            }

            if(readSMS != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionNeeded.add(Manifest.permission.RECEIVE_SMS);
            }
            if(permissionSendMessage != PackageManager.PERMISSION_GRANTED)
            {
                listPermissionNeeded.add(Manifest.permission.SEND_SMS);
            }

            if(!listPermissionNeeded.isEmpty())
            {
                ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
                        REQUEST_ID_MULTIPLE_PERMISSION);
                return false;
            }
            return true;

        }

        public void onResume() {
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
            super.onResume();
        }

        public void onPause() {
            super.onPause();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }

    }
