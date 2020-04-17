package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.Random;

public class Sendmsg extends AppCompatActivity {

    EditText phone,msg;
    Button send;
    int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmsg);

        phone = findViewById(R.id.phone);
        msg = findViewById(R.id.mess);
        send = findViewById(R.id.sendmsg);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int permissioncheck = ContextCompat.checkSelfPermission(Sendmsg.this, Manifest.permission.SEND_SMS);

            if(permissioncheck == PackageManager.PERMISSION_GRANTED)
            {
                Mymessage();
            }
            else
                ActivityCompat.requestPermissions(Sendmsg.this,new String[]{Manifest.permission.SEND_SMS}, 0);

            }
        });
    }

    private void Mymessage()
        {
            String ph = phone.getText().toString().trim();

            Random random = new Random();
            randomNumber = random.nextInt(999999);
            String otp = String.valueOf(randomNumber); // converting our integer otp to string because int cant be shared in sms manager

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(ph, null,otp, null, null);

            //Toast.makeText(this,"Message sent"+tmpS, Toast.LENGTH_LONG).show();
            Intent in = new Intent(Sendmsg.this,validation.class);
            in.putExtra("send_otp",otp);  // send_otp variable contain otp.. sended to second page
            startActivity(in);

        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 0:

                if(grantResults.length>=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
                {
                    Mymessage();
                }
                else
                {
                    Toast.makeText(this,"You Dont have permission",Toast.LENGTH_LONG).show();
                }

        }

    }
}
