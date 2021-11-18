package com.example.helping_hand.DetailPage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.helping_hand.DataModel.DetailsListModel;
import com.example.helping_hand.DataModel.MenuListModel;
import com.example.helping_hand.DataModel.ToolsDetailListModel;
import com.example.helping_hand.DataModel.UserData;
import com.example.helping_hand.DataProvider.DataProvider;
import com.example.helping_hand.Feedback;
import com.example.helping_hand.HomeScreen;
import com.example.helping_hand.R;
import com.example.helping_hand.SubMenuPage.SubMenuPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailsPageActivity extends AppCompatActivity implements DetailPageRecyclerViewAdapter.ItemClickListener {
    private int pageType;
    private int linktype;//0 for tools section on site /1 for robu
    private String uniqueId;
    private RecyclerView recyclerView;
    private MenuListModel.MenuItem previousItemDetails;
    List<UserData> finalUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        DataProvider.fetchDiyMenuList(this);
        ImageButton feedback = (ImageButton) findViewById(R.id.IBfeed);
        ImageButton hyper = (ImageButton) findViewById(R.id.IBparts);
        TextView navname = (TextView) findViewById(R.id.TVnavname);

        recyclerView = findViewById(R.id.RV_Detail_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfeedback=new Intent(DetailsPageActivity.this, Feedback.class);
                startActivity(intentfeedback);
            }
        });
        hyper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = checkLinkType();
                Intent hyperintent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(hyperintent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pageType = bundle.getInt("categories", 0);
        }

        previousItemDetails = (MenuListModel.MenuItem) getIntent().getSerializableExtra("menuItemModel");
        if (previousItemDetails != null) {
            uniqueId = previousItemDetails.uniqueId;
            navname.setText(previousItemDetails.title);
            fetchMenuList();
        }
        testWriteData();
    }

    String checkLinkType() {
        String diylink="https://robu.in/";
        String toollink="https://www.amazon.in/Power-Tools/b?ie=UTF8&node=4286663031";
        if(pageType == HomeScreen.Categories.Diy.ordinal()){
            return diylink;
        } else {
            return toollink;
        }
    }

    public void setUpRecyclerView(String uniqueId, ArrayList<DetailsListModel.Details> diyDetailStepsArrayList, ArrayList<ToolsDetailListModel.ToolDetailModel> toolsDetailsArrayList) {
        // set up the RecyclerView
        if (pageType == HomeScreen.Categories.Diy.ordinal()) {
            ArrayList<DetailsListModel.Details.Steps> stepsArrayList = getDiyStepsArrayList(uniqueId, diyDetailStepsArrayList);
            if(stepsArrayList != null) {
                DetailPageRecyclerViewAdapter adapter = new DetailPageRecyclerViewAdapter(this, stepsArrayList);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);
            }
        } else {
            ArrayList<ToolsDetailListModel.ToolDetailModel> stepsArrayList = getToolDetailsList(uniqueId, toolsDetailsArrayList);
            if(stepsArrayList != null) {
                ToolsDetailPageRecyclerViewAdapter adapter = new ToolsDetailPageRecyclerViewAdapter(this, stepsArrayList);
//                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);
            }
        }
    }

    private ArrayList<DetailsListModel.Details.Steps> getDiyStepsArrayList(String uniqueId, ArrayList<DetailsListModel.Details> diyDetailStepsArrayList) {
        ArrayList<DetailsListModel.Details.Steps> stepsArrayList = null;
        for (DetailsListModel.Details currentData: diyDetailStepsArrayList) {
            if(currentData.uniqueId.contentEquals(uniqueId)) {
                stepsArrayList=currentData.stepsArrayList;
                break;
            }
        }
        return stepsArrayList;
    }

    private ArrayList<ToolsDetailListModel.ToolDetailModel> getToolDetailsList(String uniqueId, ArrayList<ToolsDetailListModel.ToolDetailModel> toolsDetailsArrayList) {
        ArrayList<ToolsDetailListModel.ToolDetailModel> toolDetailModelArrayList = null;
        for (ToolsDetailListModel.ToolDetailModel currentData: toolsDetailsArrayList) {
            if(currentData.uniqueId.contentEquals(uniqueId)) {
                toolDetailModelArrayList=new ArrayList<>(Arrays.asList(currentData));
                break;
            }
        }
        return toolDetailModelArrayList;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void fetchMenuList() {
        // check screen Type from enum
        DatabaseReference databaseReference;
        if (pageType == HomeScreen.Categories.Diy.ordinal()) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("diy_details_list");
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("tool_details_list");
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (pageType == HomeScreen.Categories.Diy.ordinal()) {
                    loadDiyDetailsStepsList(snapshot);
                } else {
                    loadToolsDetailsStepsList(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }

    private void loadDiyDetailsStepsList(@NonNull DataSnapshot snapshot) {
        ArrayList<DetailsListModel.Details> detailsArrayList = new ArrayList<>();
        for(DataSnapshot childElement : snapshot.getChildren()) {
            GenericTypeIndicator<List<DetailsListModel.Details.Steps>> stepType = new GenericTypeIndicator<List<DetailsListModel.Details.Steps>>() {};
            DetailsListModel.Details data = childElement.getValue(DetailsListModel.Details.class);
            List<DetailsListModel.Details.Steps> stepsList = childElement.child("steps").getValue(stepType);
            if (stepsList != null && data != null) {
                data.stepsArrayList = (ArrayList<DetailsListModel.Details.Steps>) stepsList;
                detailsArrayList.add(data);
            }
        }
        setUpRecyclerView(uniqueId, detailsArrayList, null);
    }

    private void loadToolsDetailsStepsList(@NonNull DataSnapshot snapshot) {
        GenericTypeIndicator<List<ToolsDetailListModel.ToolDetailModel>> stepType = new GenericTypeIndicator<List<ToolsDetailListModel.ToolDetailModel>>() {};
        List<ToolsDetailListModel.ToolDetailModel> toolDetailModelList = snapshot.getValue(stepType);
        setUpRecyclerView(uniqueId, null, (ArrayList<ToolsDetailListModel.ToolDetailModel>) toolDetailModelList);
    }



    private void testWriteData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("userData");

        String usedID = FirebaseAuth.getInstance().getUid();
        ArrayList<MenuListModel.MenuItem> menuItemList = new ArrayList<>();

        menuItemList.add(previousItemDetails);
        UserData udata = new UserData();
        udata.userID = usedID;
        udata.userAge = 26;
        udata.userGender = SubMenuPageActivity.Gender.Male.toString();
        udata.userName = "blabla";
        udata.userFavourites = menuItemList;

        Log.d("map","" + udata.toMap().toString());

        //Update data on server
        databaseReference.push().setValue(finalUserData);
    }

    private void updateFavourites(DataSnapshot snapshot, String userID, MenuListModel.MenuItem newFavModel) {
        if (finalUserData != null) {
            finalUserData = new ArrayList<UserData>();
        } else {
            finalUserData = null;
        }

        List<MenuListModel.MenuItem> finalFavList = new ArrayList<MenuListModel.MenuItem>();

        for(DataSnapshot childElement: snapshot.getChildren()) {
            finalFavList.clear();
            UserData userData = childElement.getValue(UserData.class);

            if (userData!= null && userData.userID.contentEquals(userID)) {

                GenericTypeIndicator<List<MenuListModel.MenuItem>> favListType = new GenericTypeIndicator<List<MenuListModel.MenuItem>>() {};
                List<MenuListModel.MenuItem> favList = childElement.child("userFavourites").getValue(favListType);

                if (favList != null && favList.isEmpty()) {

                    Boolean favModelExist = false;
                    Integer existingCount = 0;

                    for (MenuListModel.MenuItem existingFavModel : userData.userFavourites) {
                        if (existingFavModel.uniqueId.contentEquals(newFavModel.uniqueId)) {
                            favModelExist = true;
                            break;
                        } else {
                            favModelExist = false;
                        }
                        existingCount ++;
                    }
                    finalFavList.addAll(favList);

                    if (favModelExist) {
                        finalFavList.remove(existingCount);
                    } else {
                        finalFavList.add(newFavModel);
                    }
                }
                userData.userFavourites = finalFavList;
                finalUserData.add(userData);
            }
        }
    }
}

