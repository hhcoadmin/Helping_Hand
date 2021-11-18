package com.example.helping_hand.DataProvider;

import android.content.Context;

import com.example.helping_hand.DataModel.DetailsListModel;
import com.example.helping_hand.DataModel.MenuListModel;
import com.example.helping_hand.DataModel.ToolsDetailListModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class DataProvider extends Object {


    public static MenuListModel fetchDiyMenuList(Context context) {
        String jsonString = getJsonFromAssets(context,"menu_list.json");
        Gson gson = new Gson();
        MenuListModel listModel = gson.fromJson(jsonString, MenuListModel.class);
        return listModel;
    }

    public static DetailsListModel fetchDiyDetailList(Context context) {
        String jsonString = getJsonFromAssets(context,"details_list.json");
        Gson gson = new Gson();
        DetailsListModel detailModel = gson.fromJson(jsonString, DetailsListModel.class);
        return detailModel;
    }

    public static MenuListModel fetchToolsList(Context context) {
        String jsonString = getJsonFromAssets(context,"tool_submenu.json");
        Gson gson = new Gson();
        MenuListModel toolsListModel = gson.fromJson(jsonString, MenuListModel.class);
        return toolsListModel;
    }

    public static ToolsDetailListModel fetchToolsDetailsList(Context context) {
        String jsonString = getJsonFromAssets(context,"tools_details.json");
        Gson gson = new Gson();
        ToolsDetailListModel toolsDetailsListModel = gson.fromJson(jsonString, ToolsDetailListModel.class);
        return toolsDetailsListModel;
    }

    static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream file = context.getAssets().open(fileName);

            int size = file.available();
            byte[] buffer = new byte[size];
            file.read(buffer);
            file.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}
