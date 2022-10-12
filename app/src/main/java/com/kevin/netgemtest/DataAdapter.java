package com.kevin.netgemtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kevin.netgemtest.Activity.GifDisplayActivity;
import com.kevin.netgemtest.Model.DataModel;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    Context context;
    ArrayList<DataModel> modelList;
    DataModel mydata;


    private OnItemClickListener onItemClickListener;

    public DataAdapter(Context context, ArrayList<DataModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    //create interface for handling clicks
    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        DataModel dataModel = modelList.get(position);

        Glide.with(context).load(dataModel.getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.searchgifView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    String imageUrl = modelList.get(position).getImageUrl();
                    Toast.makeText(context.getApplicationContext(), "onclick :" +position +imageUrl, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, GifDisplayActivity.class);
                    intent.putExtra("imageUrl",imageUrl);
                    context.startActivity(intent);

                    if (onItemClickListener!=null){
                      //  int position = getBindingAdapterPosition();
                        Toast.makeText(context.getApplicationContext(), "onclick :" +position, Toast.LENGTH_SHORT).show();
                        if(position!=RecyclerView.NO_POSITION){
                            onItemClickListener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
