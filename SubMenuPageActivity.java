package com.example.helping_hand.SubMenuPage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.helping_hand.DataModel.MenuListModel;
import com.example.helping_hand.DataModel.UserData;
import com.example.helping_hand.DataProvider.DataProvider;
import com.example.helping_hand.DetailPage.DetailsPageActivity;
import com.example.helping_hand.Feedback;
import com.example.helping_hand.HomeScreen;
import com.example.helping_hand.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubMenuPageActivity extends AppCompatActivity implements SubMenuRecyclerViewAdapter.ItemClickListener {
    private SubMenuRecyclerViewAdapter adapter;
    ImageButton feedback;
    private TextView topName;
    private RecyclerView recyclerView;
    private int pageType;

    public enum Gender {
        Male,Female,Other;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        topName = (TextView) findViewById(R.id.TVnavname);
        Bundle bundle=getIntent().getExtras();
        pageType = bundle.getInt("categories", 0);

        feedback=(ImageButton) findViewById(R.id.IBfeed);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfeedback=new Intent(SubMenuPageActivity.this, Feedback.class);
                startActivity(intentfeedback);
            }
        });

        // set up the RecyclerVie
        configureRecyclerView();
        //fetch data online
        fetchMenuList();
//        testWriteData();
    }

    private void configureRecyclerView() {
        recyclerView = findViewById(R.id.rvlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupDataOnRecyclerView(ArrayList<MenuListModel.MenuItem> dataList) {
        adapter = new SubMenuRecyclerViewAdapter(this, dataList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        MenuListModel.MenuItem cellData = adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putInt("categories",pageType);
        Intent intentDetails= new Intent(SubMenuPageActivity.this, DetailsPageActivity.class);
        intentDetails.putExtra("menuItemModel", cellData);
        intentDetails.putExtras(bundle);
        startActivity(intentDetails);
    }

    private void fetchMenuList() {
        // check screen Type from enum
        DatabaseReference databaseReference;
        if (pageType == HomeScreen.Categories.Diy.ordinal()) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("diy_topic_list");
            topName.setText("Diy Projects");
        } else if(pageType == HomeScreen.Categories.Favorite.ordinal()) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("userData");
            topName.setText("Favorites");
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("tool_topic_list");
            topName.setText("Tools Guide");
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MenuListModel.MenuItem> menuItemList = new ArrayList<>();

                for(DataSnapshot childElement : snapshot.getChildren()){
                    MenuListModel.MenuItem data = childElement.getValue(MenuListModel.MenuItem.class);
                    menuItemList.add(data);
                }
                setupDataOnRecyclerView(menuItemList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
}


