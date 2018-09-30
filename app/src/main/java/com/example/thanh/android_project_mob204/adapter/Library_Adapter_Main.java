package com.example.thanh.android_project_mob204.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.model.Library;

import java.util.List;

public class Library_Adapter_Main extends RecyclerView.Adapter<Library_Adapter_Main.ViewHolder>{
    private List<Library> listLibrary;

    public Library_Adapter_Main(List<Library> listLibrary) {
        this.listLibrary = listLibrary;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewLibrary = inflater.inflate(R.layout.item_library, parent, false);
        return new ViewHolder(viewLibrary);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Library library = listLibrary.get(position);
        holder.imgAvatarLibrary.setImageResource(library.getAvatar());
        holder.tvTitleLibrary.setText(library.getTitle());
        holder.tvSubTitle.setText(library.getSubTitle());
    }

    @Override
    public int getItemCount() {
        return listLibrary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitleLibrary, tvSubTitle;
        public ImageView imgAvatarLibrary;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitleLibrary = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitleLibrary);
            imgAvatarLibrary = itemView.findViewById(R.id.imgAvatarLibrary);
        }
    }
}
