package com.solanki.sahil.kisan.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solanki.sahil.kisan.R;
import com.solanki.sahil.kisan.data.model.ModelMessage;

import java.util.List;


public class MyRecyclerViewAdapter_Message extends RecyclerView
        .Adapter<MyRecyclerViewAdapter_Message
        .DataObjectHolder> {

    private List<ModelMessage> mDataset;
    private LayoutInflater layoutInflater;

    public MyRecyclerViewAdapter_Message(List<ModelMessage> myDataset) {
        mDataset = myDataset;
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }


        View view = layoutInflater.inflate(R.layout.activity_list_messages, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.number.setText(mDataset.get(position).getNumber());
        holder.otp.setText(mDataset.get(position).getBody());
        holder.date.setText(mDataset.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView number, otp, date;


        public DataObjectHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            otp = itemView.findViewById(R.id.otp);
            date = itemView.findViewById(R.id.date);

        }

    }
}
