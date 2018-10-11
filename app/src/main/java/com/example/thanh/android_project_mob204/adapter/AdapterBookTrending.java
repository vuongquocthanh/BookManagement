package com.example.thanh.android_project_mob204.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.model.BookTrending;

import java.util.List;

public class AdapterBookTrending extends RecyclerView.Adapter<AdapterBookTrending.ViewHolder>{

    private List<BookTrending> listBookTrending;

    public AdapterBookTrending(List<BookTrending> listBookTrending) {
        this.listBookTrending = listBookTrending;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_book_trending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookTrending bookTrending = listBookTrending.get(position);
        holder.tvBookTitle.setText(bookTrending.getBookName());
        holder.tvBookContent.setText(bookTrending.getBookDescription());
        holder.tvValueQuantity.setText(bookTrending.getCountTrending()+"");

        byte[] avatarBook = bookTrending.getBookAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatarBook, 0, avatarBook.length);
        holder.imgBookAvatar.setImageBitmap(bitmap);


    }

    @Override
    public int getItemCount() {
        return listBookTrending.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvBookTitle, tvBookContent, tvValueQuantity;
        public ImageView imgBookAvatar;
        public ViewHolder(View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookContent = itemView.findViewById(R.id.tvBookContent);
            tvValueQuantity = itemView.findViewById(R.id.tvValueQuantity);
            imgBookAvatar = itemView.findViewById(R.id.imgBookAvatar);
        }
    }
}
