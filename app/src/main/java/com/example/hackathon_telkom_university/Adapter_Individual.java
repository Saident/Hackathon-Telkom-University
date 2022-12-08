package com.example.hackathon_telkom_university;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Individual extends RecyclerView.Adapter<Adapter_Individual.MyView> {
    private List<Class_Coffee> list;
    private Context context;

    public void setFilteredList(List<Class_Coffee> filteredList){
        this.list = filteredList;
        notifyDataSetChanged();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView name;
        ImageView images;
        Button love;

        public MyView(View view) {
            super(view);
            name = itemView.findViewById(R.id.rec_name);
            images = itemView.findViewById(R.id.iv_bg_coffee);
            love = itemView.findViewById(R.id.add_fav);
        }
    }

    public Adapter_Individual(Context context, List<Class_Coffee> VerticalLayout) {
        this.context = context;
        this.list = (ArrayList<Class_Coffee>) VerticalLayout;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_individual, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        Class_Coffee coffee = list.get(position);
        holder.name.setText(coffee.getNameCoffee());
        Glide.with(context).load(coffee.getImageref()).into(holder.images);
        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("fav").child(coffee.getName())
                        .setValue(coffee);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}