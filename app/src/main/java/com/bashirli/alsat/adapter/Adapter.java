package com.bashirli.alsat.adapter;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bashirli.alsat.app.MainFragmentDirections;
import com.bashirli.alsat.app.SearchFragmentDirections;
import com.bashirli.alsat.databinding.RecyclerBinding;
import com.bashirli.alsat.model.Data;
import com.bashirli.alsat.view.FeedActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterHolder> {
    ArrayList<Data> arrayList;

    public Adapter(ArrayList<Data> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       RecyclerBinding binding=RecyclerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
        holder.binding.editTextTextPersonName6.setText(arrayList.get(position).getProduct());
        holder.binding.editTextTextPersonName7.setText("Qiymət : "+arrayList.get(position).getValue()+" AZN");
        Picasso.get().load(arrayList.get(position).getImage()).fit().into(holder.binding.imageView2);
        holder.binding.editTextTextPersonName7.setInputType(InputType.TYPE_NULL);
        holder.binding.editTextTextPersonName6.setInputType(InputType.TYPE_NULL);
        holder.binding.recyclerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragmentDirections.ActionMainFragmentToAddPostFragment action= MainFragmentDirections.actionMainFragmentToAddPostFragment();
                action.setInfo("old");
                action.setGeri("false");
                action.setMainData(arrayList.get(position));
                Navigation.findNavController(holder.itemView).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{
        private RecyclerBinding binding;
        public AdapterHolder(@NonNull RecyclerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
