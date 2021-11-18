package com.example.helping_hand.DataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailsListModel implements Serializable {

    @SerializedName("Details")
    public ArrayList<Details> detailsArrayList;

    public static class Details implements Serializable{
        @SerializedName("uniqueId")
        public String uniqueId;
        @SerializedName("steps")
        public ArrayList<Steps> stepsArrayList;

        public static class Steps implements Serializable{
            @SerializedName("stepNumber")
            public String stepNumber;
            @SerializedName("heading")
            public String heading;
            @SerializedName("stepContent")
            public String stepContent;
            @SerializedName("imageUrl")
            public String imageUrl;
        }
    }
}