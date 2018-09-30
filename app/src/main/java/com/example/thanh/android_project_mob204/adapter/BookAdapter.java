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
import com.example.thanh.android_project_mob204.model.Book;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private List<Book> listBook;
    private LayoutInflater inflater;
    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listBook = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_listview_book, parent, false);
            holder.imgAvatar = convertView.findViewById(R.id.imgAvatar);
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);
            holder.tvContent = convertView.findViewById(R.id.tvContent);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Book book = listBook.get(position);
        holder.imgAvatar.setImageResource(book.getAvatar());
        holder.tvTitle.setText(book.getTitle());
        holder.tvContent.setText(book.getContent());
        return convertView;
    }

    public class ViewHolder{
        public TextView tvTitle, tvContent;
        public ImageView imgAvatar;
    }
}
