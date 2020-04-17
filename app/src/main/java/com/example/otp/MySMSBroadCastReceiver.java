package com.example.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MySMSBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str ="";

        if (bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];

            for (int i=0;i<smsm.length;i++)
            {
                smsm[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                sms_str += "\r\nMessage: ";
                sms_str += smsm[i].getMessageBody().toString();
                sms_str+= "\r\n";

                String Sender = smsm[i].getOriginatingAddress();

                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message",sms_str);

                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);
            }

        }
    }
}
