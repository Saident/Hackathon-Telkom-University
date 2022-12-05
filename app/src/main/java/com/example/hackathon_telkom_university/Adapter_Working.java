package com.example.hackathon_telkom_university;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Working extends RecyclerView.Adapter<Adapter_Working.MyView> {
    private ArrayList<Class_Coffee> list;
    private Context context;

    public class MyView extends RecyclerView.ViewHolder {
        TextView name;
        ImageView images;

        public MyView(View view) {
            super(view);
            name = itemView.findViewById(R.id.rec_name);
            images = itemView.findViewById(R.id.iv_bg_coffee);
        }
    }

    public Adapter_Working(Context context, List<Class_Coffee> horizontalList) {
        this.context = context;
        this.list = (ArrayList<Class_Coffee>) horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_working, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        Class_Coffee coffee = list.get(position);
        holder.name.setText(coffee.getNameCoffee());
        Glide.with(context).load(coffee.getImageref()).into(holder.images);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}