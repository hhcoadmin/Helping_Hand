package com.example.helping_hand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helping_hand.Login.LoginDatabase;

public class Feedback extends AppCompatActivity {
    LoginDatabase db;
    EditText etto, etsubject,etmessage;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        db = new LoginDatabase(this);
        etto=(EditText) findViewById(R.id.etemial);
        etsubject =(EditText) findViewById(R.id.etsubject);
        etmessage=(EditText) findViewById(R.id.etmessage);
        send=(Button) findViewById(R.id.Bsubmit);

        etto.setEnabled(false);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmail();

            }

            private void sendmail() {
                String recipientlist=etto.getText().toString();
                String[] recipient=recipientlist.split(",");//store emails and split them by comma

                String subject= etsubject.getText().toString();
                String meesage=etmessage.getText().toString();
                if(meesage.isEmpty() || subject.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Fill in Both the Detials",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(Intent.ACTION_SEND); // give option to send more then 1 app
                    intent.putExtra(Intent.EXTRA_EMAIL,recipient);
                    intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                    intent.putExtra(Intent.EXTRA_TEXT,meesage);


                    intent.setType("message/rfc822");//rfc822
                    startActivity(Intent.createChooser(intent,"Chose an app"));
                };
            }
        });
    }
}