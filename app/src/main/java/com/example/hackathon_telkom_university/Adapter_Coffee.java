package com.example.hackathon_telkom_university;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Coffee extends RecyclerView.Adapter<Adapter_Coffee.MyView> {
    private List<String> list;

    public class MyView extends RecyclerView.ViewHolder {
        TextView textView;

        public MyView(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.rec_name);
        }
    }

    public Adapter_Coffee(List<String> horizontalList) {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coffee, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}