package com.example.helping_hand.DataModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserData implements Serializable {

    @SerializedName("userID")
    public String userID;
    @SerializedName("userName")
    public String userName;
    @SerializedName("userGender")
    public String userGender;
    @SerializedName("userAge")
    public Integer userAge;
    @SerializedName("userFavourites")
    public List<MenuListModel.MenuItem> userFavourites;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userID", userID);
        result.put("userName", userName);
        result.put("userGender", userGender);
        result.put("userAge", userAge);
        result.put("userFavourites", convertFavListToMap());
        return result;
    }

    private List<Map<String, Object>> convertFavListToMap() {
        List<Map<String, Object>> favList = new ArrayList<Map<String, Object>>();
        for (MenuListModel.MenuItem data: userFavourites) {
            favList.add(data.toMap());
        }
        return favList;
    }
}
