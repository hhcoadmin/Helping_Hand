package com.example.helping_hand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserPage extends AppCompatActivity {
    ImageView userimage;
    Button updateData;
    TextView username;
    int Gender = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.GenderGroup);
        updateData = (Button) findViewById(R.id.UpdateButton);
        userimage= (ImageView)findViewById(R.id.Userimage);

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ID = radioGroup.getCheckedRadioButtonId();
                RadioButton raddiobutton = (RadioButton) findViewById(ID);
                String type = raddiobutton.getText().toString();


                String link = checkLinkType(type);
                Glide.with(UserPage.this).load(link).placeholder(R.drawable.placeholder_image).into(userimage);

            }
        });
    }

    private String checkLinkType(String Gender) {
        String URLF="https://user-images.githubusercontent.com/88957848/141816733-86738162-1688-4f04-887c-94e09f4677c4.png";
        String URLM="https://user-images.githubusercontent.com/88957848/141816738-742ed719-0098-40c3-9d5e-51fd750840f9.png";

        if(Gender.equals("Male")){
            return URLM;
        }else{
            return URLF;
        }
    }

}