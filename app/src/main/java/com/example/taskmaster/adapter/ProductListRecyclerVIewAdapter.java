package com.example.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.taskmaster.Model.Task;
import com.amplifyframework.datastore.generated.model.Task;
import com.example.taskmaster.Edit_Activity;
import com.example.taskmaster.R;
import com.example.taskmaster.TaskDetailPage;

import java.util.List;

public class ProductListRecyclerVIewAdapter extends RecyclerView.Adapter{

    List<Task> Tasks;
    Context callingActivity;

    public ProductListRecyclerVIewAdapter (List<Task> Tasks , Context callingActivity) {
        this.Tasks = Tasks;
        this.callingActivity=callingActivity;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ProductFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_product_list,parent,false);
        return new  ProductListViewHolder (ProductFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView productFragmentTextView = (TextView) holder.itemView.findViewById(R.id.Textview4);
        String Id = Tasks.get(position).getId();
        String productName = Tasks.get(position).getTitle();
        String getBody = Tasks.get(position).getBody();
        String getdate = Tasks.get(position).getCreatedAt().toString();
        String getstate = Tasks.get(position).getStateofTask().toString();
        String getTeam = Tasks.get(position).getTeamTask().getTeamName().toString();
        productFragmentTextView.setText(position+1 +". "+ productName);
        ////////////////////////////////////////////////////////////////
        View productViewHolder = holder.itemView;
        productViewHolder.setOnClickListener(view -> {
            Intent goToOrderFormIntent = new Intent(callingActivity, Edit_Activity.class);
//            goToOrderFormIntent.putExtra("Id", String.valueOf(Id));
            goToOrderFormIntent.putExtra("Id", Id);
            goToOrderFormIntent.putExtra("key", productName   );
            goToOrderFormIntent.putExtra("getBody", getBody);
            goToOrderFormIntent.putExtra("getdate", getdate);
            goToOrderFormIntent.putExtra("getstate", getstate);
            goToOrderFormIntent.putExtra("getTeam", getTeam);
            callingActivity.startActivity(goToOrderFormIntent);
        });
    }

    @Override
    public int getItemCount() {
        return Tasks.size();
    }

    public static class ProductListViewHolder extends RecyclerView.ViewHolder{

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
