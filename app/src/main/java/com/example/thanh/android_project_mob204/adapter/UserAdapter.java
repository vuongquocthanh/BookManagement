package com.example.thanh.android_project_mob204.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.interfaces.OnItemDeleteListener;
import com.example.thanh.android_project_mob204.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private List<User> listUser;
    private LayoutInflater inflater;
    private OnItemDeleteListener deleteListener;
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects, OnItemDeleteListener listener) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listUser = objects;
        deleteListener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_user, parent, false);
            holder.imgAvatar = convertView.findViewById(R.id.imgAvatar);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvPhone = convertView.findViewById(R.id.tvPhone);
            holder.imgDelete = convertView.findViewById(R.id.imgDelete);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        User user = listUser.get(position);
        if(position%3==0){
            holder.imgAvatar.setImageResource(R.drawable.user1);
        }else if(position%3==1){
            holder.imgAvatar.setImageResource(R.drawable.user2);
        }else{
            holder.imgAvatar.setImageResource(R.drawable.user3);
        }
        holder.tvName.setText(user.getName());
        holder.tvPhone.setText(user.getPhonenumber());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                builder.setTitle(R.string.titleDialog);
                builder.setMessage(R.string.question);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteListener.OnItemDeleted(position);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        return convertView;
    }

    public class ViewHolder{
        public ImageView imgAvatar, imgDelete;
        public TextView tvName, tvPhone;
    }
}
