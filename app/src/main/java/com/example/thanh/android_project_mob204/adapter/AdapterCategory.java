package com.example.thanh.android_project_mob204.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Category;
import com.example.thanh.android_project_mob204.sqlitedao.DAOCategory;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {
    private List<Category> listCategory;
    private DatabaseHelper databaseHelper;
    private DAOCategory daoCategory;
    public AdapterCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewCategory = inflater.inflate(R.layout.item_category, parent, false);
        databaseHelper = new DatabaseHelper(parent.getContext());
        daoCategory = new DAOCategory(databaseHelper);
        return new ViewHolder(viewCategory);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = listCategory.get(position);
        holder.tvTitleCategory.setText(category.getCategoryName());
//        Chuyển byte[] => bitmap
        byte[] avatarCategory = category.getCategoryAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatarCategory, 0, avatarCategory.length);
        holder.imgCategoryAvatar.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }
    public void removeItem(int position) {
        listCategory.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTitleCategory;
        public ImageView imgCategoryAvatar, imgCategoryDelete, imgCategoryEdit, imgCategoryRead;
        public ViewHolder(final View itemView) {
            super(itemView);
            tvTitleCategory = itemView.findViewById(R.id.tvTitleCategory);
            imgCategoryAvatar = itemView.findViewById(R.id.imgAvatarCategory);
            imgCategoryRead = itemView.findViewById(R.id.imgRead);
            imgCategoryDelete = itemView.findViewById(R.id.imgDelete);
            imgCategoryEdit = itemView.findViewById(R.id.imgEditCategory);
            imgCategoryDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    builder.setTitle("Delete");
                    builder.setMessage("Do you want delete?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            daoCategory.deleteCategory(listCategory.get(getAdapterPosition()));
                            removeItem(getAdapterPosition());
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
            imgCategoryEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    builder.setTitle("Category Information");
                    LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialogEditCategory = inflater.inflate(R.layout.dialog_edit_category, null);
                    TextInputLayout tilEditCategoryId = viewDialogEditCategory.findViewById(R.id.tilEditCategoryId);
                    TextInputLayout tilEditCategoryName = viewDialogEditCategory.findViewById(R.id.tilEditCategoryName);
                    TextInputLayout tilEditCategoryDescription = viewDialogEditCategory.findViewById(R.id.tilEditCategoryDescription);
                    TextInputLayout tilEditCategoryPosition = viewDialogEditCategory.findViewById(R.id.tilEditCategoryPosition);
                    final EditText edEditCategoryId = viewDialogEditCategory.findViewById(R.id.edEditCategoryId);
                    final EditText edEditCategoryName = viewDialogEditCategory.findViewById(R.id.edEditCategoryName);
                    final EditText edEditCategoryDescription = viewDialogEditCategory.findViewById(R.id.edEditCategoryDescription);
                    final EditText edEditCategoryPosition = viewDialogEditCategory.findViewById(R.id.edEditCategoryPosition);
                    edEditCategoryId.setText(daoCategory.getAllCategory().get(getAdapterPosition()).getCategoryID());
                    edEditCategoryName.setText(daoCategory.getAllCategory().get(getAdapterPosition()).getCategoryName());
                    edEditCategoryDescription.setText(daoCategory.getAllCategory().get(getAdapterPosition()).getCategoryDescription());
                    edEditCategoryPosition.setText(daoCategory.getAllCategory().get(getAdapterPosition()).getCategoryPosition());
                    builder.setView(viewDialogEditCategory);
                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listCategory.get(getAdapterPosition()).setCategoryID(edEditCategoryId.getText().toString().trim());
                            listCategory.get(getAdapterPosition()).setCategoryName(edEditCategoryName.getText().toString().trim());
                            listCategory.get(getAdapterPosition()).setCategoryDescription(edEditCategoryDescription.getText().toString().trim());
                            listCategory.get(getAdapterPosition()).setCategoryPosition(edEditCategoryPosition.getText().toString().trim());
                            daoCategory.updateCategory(new Category(edEditCategoryId.getText().toString(), edEditCategoryName.getText().toString(),listCategory.get(getAdapterPosition()).getCategoryAvatar() , edEditCategoryDescription.getText().toString(), edEditCategoryPosition.getText().toString()));
                            tvTitleCategory.setText(edEditCategoryName.getText().toString());
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}
