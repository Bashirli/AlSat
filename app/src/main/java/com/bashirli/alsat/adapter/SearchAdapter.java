package com.bashirli.alsat.adapter;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bashirli.alsat.app.MainFragmentDirections;
import com.bashirli.alsat.app.MyPostsFragmentDirections;
import com.bashirli.alsat.app.SearchFragmentDirections;
import com.bashirli.alsat.databinding.RecyclerBinding;
import com.bashirli.alsat.model.Data;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    ArrayList<Data> arrayList;

    public SearchAdapter(ArrayList<Data> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerBinding binding=RecyclerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SearchHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        holder.binding.editTextTextPersonName6.setText(arrayList.get(position).getProduct());
        holder.binding.editTextTextPersonName7.setText("Qiymət : "+arrayList.get(position).getValue()+" AZN");
        holder.binding.editTextTextPersonName7.setInputType(InputType.TYPE_NULL);
        holder.binding.editTextTextPersonName6.setInputType(InputType.TYPE_NULL);
        Picasso.get().load(arrayList.get(position).getImage()).into(holder.binding.imageView2);
        holder.binding.recyclerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SearchFragmentDirections.ActionSearchFragmentToAddPostFragment action= SearchFragmentDirections.actionSearchFragmentToAddPostFragment();
                    action.setInfo("old");
                    action.setGeri("true");
                    action.setMainData(arrayList.get(position));
                    Navigation.findNavController(holder.itemView).navigate(action);
                }catch(Exception e){}
                try{
                    MyPostsFragmentDirections.ActionMyPostsFragmentToAddPostFragment action= MyPostsFragmentDirections.actionMyPostsFragmentToAddPostFragment();
                    action.setInfo("old");
                    action.setGeri("trueM");
                    action.setMainData(arrayList.get(position));
                    Navigation.findNavController(holder.itemView).navigate(action);

                } catch (Exception e){

                }

                }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SearchHolder extends RecyclerView.ViewHolder{
        private RecyclerBinding binding;
        public SearchHolder(@NonNull RecyclerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
