package com.example.helping_hand.DataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToolsListModel implements Serializable {

    @SerializedName("tool_list")
    public ArrayList<ToolList> toolListArrayList;

    public static class ToolList implements Serializable{
        @SerializedName("type")
        public int type;
        @SerializedName("uniqueId")
        public String uniqueId;
        @SerializedName("title")
        public String title;
        @SerializedName("imageUrl")
        public String imageUrl;
    }
}
