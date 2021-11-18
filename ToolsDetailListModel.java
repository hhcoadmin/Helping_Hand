package com.example.helping_hand.DataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ToolsDetailListModel implements Serializable {

    @SerializedName("tool_details_list")
    public ArrayList<ToolDetailModel> toolDetailModelArrayList;

    public static class ToolDetailModel implements Serializable{
        @SerializedName("uniqueId")
        public String uniqueId;
        @SerializedName("title")
        public String title;
        @SerializedName("Details")
        public String details;
        @SerializedName("imageUrl")
        public String imageUrl;
    }
}
