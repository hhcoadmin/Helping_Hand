package com.example.helping_hand;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.helping_hand.DataModel.ToolsListModel;
import com.example.helping_hand.SubMenuPage.SubMenuPageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreen extends AppCompatActivity {
    ImageButton diy,tools,footer,comeingsoon,about,fav,userpage,signout;
    public enum Categories{
        Diy,Tools,Favorite;
    }

    FirebaseDatabase database;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        diy=(ImageButton) findViewById(R.id.IBdiy);
        tools=(ImageButton) findViewById(R.id.IBtools);
        comeingsoon=(ImageButton) findViewById(R.id.IBcomingsoon);
        footer=(ImageButton) findViewById(R.id.IBfooter);
        about=(ImageButton) findViewById(R.id.IBinfo);

        fav=(ImageButton) findViewById(R.id.IBfavorites);
        userpage = (ImageButton) findViewById(R.id.IBuser);
        signout = (ImageButton) findViewById(R.id.IBlogout);

        diy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sublist(Categories.Diy);
            }
        });
        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sublist(Categories.Tools);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sublist(Categories.Favorite);
            }
        });
        comeingsoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon !!", Toast.LENGTH_SHORT).show();
            }
        });

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Let Me Read   Σ(っ °Д °;)っ", Toast.LENGTH_LONG).show();

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentabout=new Intent(HomeScreen.this, AboutUs.class);
                startActivity(intentabout);
            }
        });

    }


    private void sublist(Categories type) {
        Intent intentsublist = new Intent(HomeScreen.this, SubMenuPageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("categories",type.ordinal());
        intentsublist.putExtras(bundle);
        startActivity(intentsublist);
    }
}