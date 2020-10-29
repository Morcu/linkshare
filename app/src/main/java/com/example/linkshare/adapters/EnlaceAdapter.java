package com.example.linkshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linkshare.Models.Enlaces;
import com.example.linkshare.R;

import java.util.ArrayList;

public class EnlaceAdapter extends RecyclerView.Adapter<EnlaceAdapter.ViewHolder>{

    private int resource;
    private ArrayList<Enlaces> enlacesList;

    public EnlaceAdapter(ArrayList<Enlaces> enlacesList, int resource){
        this.enlacesList = enlacesList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Enlaces enlace = enlacesList.get(position);
        holder.textViewEnlace.setText(enlace.getTexto());

    }

    @Override
    public int getItemCount() {
        return enlacesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewEnlace;
        public View view;

        public  ViewHolder(View view) {
            super(view);

            this.view = view;
            this.textViewEnlace = (TextView) view.findViewById(R.id.txtTitulo);

        }
    }
}
