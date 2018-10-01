package com.example.thanh.android_project_mob204.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Book;
import com.example.thanh.android_project_mob204.model.Category;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;
import com.example.thanh.android_project_mob204.sqlitedao.DAOCategory;

import java.util.ArrayList;
import java.util.List;

public class AdapterBook extends RecyclerView.Adapter<AdapterBook.ViewHolder>{

    private List<Book> listBook;
    private DatabaseHelper databaseHelper;
    private DAOBook daoBook;
    private DAOCategory daoCategory;
    private TextInputLayout tilEditBookId;
    private EditText edEditBookId;
    private TextInputLayout tilEditBookTitle;
    private EditText edEditBookTitle;
    private TextInputLayout tilEditBookDescription;
    private EditText edEditBookDescription;
    private TextInputLayout tilEditBookAuthor;
    private EditText edEditBookAuthor;
    private TextInputLayout tilEditBookPublisher;
    private EditText edEditBookPublisher;
    private TextInputLayout tilEditBookPrice;
    private EditText edEditBookPrice;
    private TextInputLayout tilEditBookCount;
    private EditText edEditBookCount;
    private Spinner spEditBookCategoryId;
    public AdapterBook(List<Book> listBook) {
        this.listBook = listBook;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewBook = inflater.inflate(R.layout.item_listview_book, parent, false);
        databaseHelper = new DatabaseHelper(parent.getContext());
        daoBook = new DAOBook(databaseHelper);
        daoCategory = new DAOCategory(databaseHelper);
        return new ViewHolder(viewBook);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = listBook.get(position);
        holder.tvBookTitle.setText(book.getBookName());
        holder.tvBookContent.setText(book.getBookDescription());
        //        Chuyển byte[] => bitmap
        byte[] avatarBook = book.getBookAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatarBook, 0, avatarBook.length);
        holder.imgBookAvatar.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public void removeItem(int position){
        listBook.remove(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvBookTitle, tvBookContent;
        public ImageView imgBookAvatar, imgBookRead, imgBookDelete,imgBookEdit;
        public ViewHolder(final View itemView) {
            super(itemView);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
            tvBookContent = itemView.findViewById(R.id.tvBookContent);
            imgBookAvatar = itemView.findViewById(R.id.imgBookAvatar);
            imgBookRead = itemView.findViewById(R.id.imgBookRead);
            imgBookEdit = itemView.findViewById(R.id.imgBookEdit);
            imgBookDelete = itemView.findViewById(R.id.imgBookDelete);
            imgBookDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    builder.setTitle("Delete");
                    builder.setMessage("Do you want delete?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            daoBook.deleteBook(listBook.get(getAdapterPosition()));
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
            imgBookEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    builder.setTitle("Book Information");
                    LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialogEditBook = inflater.inflate(R.layout.dialog_edit_book, null);
                    tilEditBookId = viewDialogEditBook.findViewById(R.id.tilEditBookId);
                    edEditBookId = viewDialogEditBook.findViewById(R.id.edEditBookId);
                    tilEditBookTitle = viewDialogEditBook.findViewById(R.id.tilEditBookTitle);
                    edEditBookTitle = viewDialogEditBook.findViewById(R.id.edEditBookTitle);
//                    tilEditBookDescription = viewDialogEditBook.findViewById(R.id.tilEditBookDescription);
                    edEditBookDescription = viewDialogEditBook.findViewById(R.id.edEditCategoryDescription);
                    tilEditBookAuthor = viewDialogEditBook.findViewById(R.id.tilEditBookAuthor);
                    edEditBookAuthor = viewDialogEditBook.findViewById(R.id.edEditBookAuthor);
                    tilEditBookPublisher = viewDialogEditBook.findViewById(R.id.tilEditBookPublisher);
                    edEditBookPublisher = viewDialogEditBook.findViewById(R.id.edEditBookPublisher);
                    tilEditBookPrice = viewDialogEditBook.findViewById(R.id.tilEditBookPrice);
                    edEditBookPrice = viewDialogEditBook.findViewById(R.id.edEditBookPrice);
                    tilEditBookCount = viewDialogEditBook.findViewById(R.id.tilEditBookCount);
                    edEditBookCount = viewDialogEditBook.findViewById(R.id.edEditBookCount);

                    edEditBookId.setText(daoBook.getAllBook().get(getAdapterPosition()).getBookId());
                    edEditBookTitle.setText(daoBook.getAllBook().get(getAdapterPosition()).getBookName());
                    edEditBookAuthor.setText(daoBook.getAllBook().get(getAdapterPosition()).getBookAuthor());
                    edEditBookPublisher.setText(daoBook.getAllBook().get(getAdapterPosition()).getBookPublisher());
                    edEditBookPrice.setText(daoBook.getAllBook().get(getAdapterPosition()).getBookPrice());
                    edEditBookCount.setText(daoBook.getAllBook().get(getAdapterPosition()).getBookCount());
                    spEditBookCategoryId = viewDialogEditBook.findViewById(R.id.spEditBookCategoryId);
                    List<String> listCategoryName = new ArrayList<>();
                    for(int i=0; i<daoCategory.getAllCategory().size(); i++){
                        listCategoryName.add(daoCategory.getAllCategory().get(i).getCategoryName());
                    }
                    ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(itemView.getContext(), R.layout.spin,R.id.text,listCategoryName);
                    spEditBookCategoryId.setAdapter(adapterCategory);

                    builder.setView(viewDialogEditBook);
                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            daoBook.updateBook(new Book(edEditBookId.getText().toString(), edEditBookTitle.getText().toString(),
                                    daoBook.getAllBook().get(getAdapterPosition()).getBookDescription(), spEditBookCategoryId.getSelectedItem().toString().trim(),
                                    edEditBookAuthor.getText().toString(), edEditBookPublisher.getText().toString(),
                                    edEditBookPrice.getText().toString(), edEditBookCount.getText().toString(), daoBook.getAllBook().get(getAdapterPosition()).getBookAvatar()));
                            listBook.get(getAdapterPosition()).setBookId(edEditBookId.getText().toString().trim());
                            listBook.get(getAdapterPosition()).setBookName(edEditBookTitle.getText().toString().trim());
//                            listBook.get(getAdapterPosition()).setBookDescription(edEditBookDescription.getText().toString().trim());
                            listBook.get(getAdapterPosition()).setBookAuthor(edEditBookAuthor.getText().toString().trim());
                            listBook.get(getAdapterPosition()).setBookPublisher(edEditBookPublisher.getText().toString().trim());
                            listBook.get(getAdapterPosition()).setBookPrice(edEditBookPrice.getText().toString().trim());
                            listBook.get(getAdapterPosition()).setBookCount(edEditBookCount.getText().toString().trim());

                            tvBookTitle.setText(edEditBookTitle.getText().toString().trim());
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}
