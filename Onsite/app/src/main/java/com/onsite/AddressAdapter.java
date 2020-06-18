package com.onsite;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddressAdapter  extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private ArrayList<String> data;
    public AddressAdapter(ArrayList<String> data){
        this.data = data;
    }
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent,false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        String name = data.get(position);
        holder.txtTitle.setText(name);

    }

    @Override
    public int getItemCount(){
        return 0;
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        public AddressViewHolder(View itemView){
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }

}
