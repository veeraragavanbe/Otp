package com.example.otp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class validation extends AppCompatActivity {

    EditText otp;
    Button validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        otp = findViewById(R.id.otp);
        validate = findViewById(R.id.validate);

        Bundle bundle = getIntent().getExtras();  //Receiving otp from previous page
        final String message = bundle.getString("send_otp");



        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = otp.getText().toString().trim();

              if (s1.equals(message))
                {
                    Toast.makeText(validation.this,"Validated Successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(validation.this,"Failed",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
