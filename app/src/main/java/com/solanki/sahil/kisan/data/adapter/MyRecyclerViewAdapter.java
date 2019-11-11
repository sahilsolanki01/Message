package com.solanki.sahil.kisan.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.solanki.sahil.kisan.R;
import com.solanki.sahil.kisan.data.model.Contact;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    private List<Contact> mDataset;
    private LayoutInflater layoutInflater;
    private RecyclerViewClickListener mListener;


    public MyRecyclerViewAdapter(List<Contact> myDataset, RecyclerViewClickListener listener) {
        mDataset = myDataset;
        mListener = listener;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {

        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }


        View view = layoutInflater.inflate(R.layout.activity_list_items, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, mListener);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.name.setText(mDataset.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        private RecyclerViewClickListener mListener;

        public DataObjectHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mListener = listener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());

        }
    }


}
