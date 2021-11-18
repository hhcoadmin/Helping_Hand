package com.example.helping_hand.DataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListModel implements Serializable {

    @SerializedName("topic_list")
    public ArrayList<MenuItem> menuItemList;

    public static class MenuItem implements Serializable{
        @SerializedName("type")
        public int type;
        @SerializedName("uniqueId")
        public String uniqueId;
        @SerializedName("title")
        public String title;
        @SerializedName("detail")
        public String detail;
        @SerializedName("imageUrl")
        public String imageUrl;

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("type", type);
            result.put("uniqueId", uniqueId);
            result.put("title", title);
            result.put("detail", detail);
            result.put("imageUrl", imageUrl);
            return result;
        }
    }
}

