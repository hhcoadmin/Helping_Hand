package com.example.helping_hand.DetailPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.helping_hand.DataModel.DetailsListModel;
import com.example.helping_hand.DataModel.MenuListModel;
import com.example.helping_hand.R;
import com.example.helping_hand.SubMenuPage.SubMenuRecyclerViewAdapter;

import java.util.ArrayList;

public class DetailPageRecyclerViewAdapter extends RecyclerView.Adapter<DetailPageRecyclerViewAdapter.ViewHolder> {

    private ArrayList<DetailsListModel.Details.Steps> dataList;
    private final LayoutInflater mInflater;
    private DetailPageRecyclerViewAdapter.ItemClickListener mClickListener;
    private final Context context;

    // data is passed into the constructor
    DetailPageRecyclerViewAdapter(Context context, ArrayList<DetailsListModel.Details.Steps> data) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public DetailPageRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row_detailpage, parent, false);
        return new DetailPageRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(DetailPageRecyclerViewAdapter.ViewHolder holder, int position) {
        DetailsListModel.Details.Steps model = dataList.get(position);
        holder.step.setText( "Step " + (position+1) + ":");
        holder.heading.setText(model.heading.trim());
        holder.details.setText(model.stepContent.trim());
        Glide.with(context).load(model.imageUrl).placeholder(R.drawable.placeholder_image).into(holder.cellImage);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView step,details,heading;
        ImageView cellImage;
        //get id an get reference
        ViewHolder(View itemView) {
            super(itemView);
            step = itemView.findViewById(R.id.tv_step);
            heading = itemView.findViewById(R.id.tv_heading);
            details = itemView.findViewById(R.id.tv_detail);
            cellImage = itemView.findViewById(R.id.detail_imageview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    DetailsListModel.Details.Steps getItem(int id) {
        return dataList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(DetailPageRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}