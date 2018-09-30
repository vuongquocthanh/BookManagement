package com.example.thanh.android_project_mob204.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.model.More;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MoreAdapter extends ArrayAdapter<More> {
    private List<More> listMore;
    private LayoutInflater inflater;
    public MoreAdapter(@NonNull Context context, int resource, @NonNull List<More> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listMore = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_more, parent, false);
            holder.civLogo = convertView.findViewById(R.id.civLogo);
            holder.tvFunction = convertView.findViewById(R.id.tvFunction);
            holder.imgNext = convertView.findViewById(R.id.imgNext);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        More more = listMore.get(position);
        holder.civLogo.setImageResource(more.getCivLogo());
        holder.tvFunction.setText(more.getFunction());
        return convertView;
    }

    public class ViewHolder{
        public CircleImageView civLogo;
        public TextView tvFunction;
        public ImageView imgNext;
    }
}
