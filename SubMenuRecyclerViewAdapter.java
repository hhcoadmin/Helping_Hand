package com.example.helping_hand.SubMenuPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.helping_hand.DataModel.MenuListModel;
import com.example.helping_hand.R;

import java.util.ArrayList;

public class SubMenuRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MenuListModel.MenuItem> dataList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context context;

    enum Rowtype{
        Normal,Footer;
    }

    // data is passed into the constructor
    SubMenuRecyclerViewAdapter(Context context, ArrayList<MenuListModel.MenuItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.dataList = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == Rowtype.Footer.ordinal()){
            view = mInflater.inflate(R.layout.recyclerview_row_footer, parent, false);
            viewHolder = new ViewHolderTwo(view);
        }
        else {
            view = mInflater.inflate(R.layout.recyclerview_row_submenu, parent, false);
            viewHolder = new ViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == Rowtype.Footer.ordinal()){
            ViewHolderTwo viewHolder0 = (ViewHolderTwo) holder;
        }
        else{
            ViewHolder holder1 = (ViewHolder) holder;
            MenuListModel.MenuItem model = dataList.get(position);
            holder1.myTextView.setText(model.title.trim());
            if(model.detail != null){
                holder1.dec.setText(model.detail.trim());
            }
            Glide.with(context).load(model.imageUrl).placeholder(R.drawable.placeholder_image).into(holder1.cellImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = dataList.get(position).type;
        return viewType;
    }

    // binds the data to the TextView in each row

    public class ViewHolderTwo extends RecyclerView.ViewHolder {

        public ViewHolderTwo(View itemView) {
            super(itemView);

        }
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return dataList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView dec;
        ImageView cellImage;
        //get id an get reference
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvname);
            dec = itemView.findViewById(R.id.TVdetails);
            cellImage = itemView.findViewById(R.id.cell_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    MenuListModel.MenuItem getItem(int id) {
        return dataList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}