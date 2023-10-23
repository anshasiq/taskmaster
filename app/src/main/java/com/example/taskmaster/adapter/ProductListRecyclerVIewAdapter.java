package com.example.taskmaster.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmaster.R;

public class ProductListRecyclerVIewAdapter extends RecyclerView.Adapter{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ProductFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_list,parent,false);
        return new  ProductListViewHolder (ProductFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class ProductListViewHolder extends RecyclerView.ViewHolder{

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
