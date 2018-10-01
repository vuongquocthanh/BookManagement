package com.example.thanh.android_project_mob204.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.AdapterBook;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.fragment.BookFragment;
import com.example.thanh.android_project_mob204.model.Book;
import com.example.thanh.android_project_mob204.model.Category;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;
import com.example.thanh.android_project_mob204.sqlitedao.DAOCategory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddBookActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private EditText edBookId;
    private EditText edBookTitle;
    private Spinner spBookCategoryId;
    private EditText edBookAuthor;
    private EditText edBookPublisher;
    private EditText edBookPrice;
    private EditText edBookCount;
    private EditText edBookDescription;
    private ImageView imgBookAvatar;
    private Button btChooseBookAvatar;
    private Button btSaveBook;
    private Button btCancelBook;
    private Button btShowBook;
    private DatabaseHelper databaseHelper;
    private DAOCategory daoCategory;
    private DAOBook daoBook;
    private int REQUSET_CODE_GALLERY = 456;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initViews();
        toolBar.setNavigationIcon(R.drawable.ic_nav);
        toolBar.setTitle("Add Book");
        toolBar.setTitleTextColor(getColor(R.color.colorAccent));
        setSupportActionBar(toolBar);
        toolBar.setTitleMarginStart(30);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<String> listCategoryName = new ArrayList<>();
        for(int i=0; i<daoCategory.getAllCategory().size(); i++){
            listCategoryName.add(daoCategory.getAllCategory().get(i).getCategoryName());
        }
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, R.layout.spin,R.id.text,listCategoryName);
        spBookCategoryId.setAdapter(adapterCategory);
        spBookCategoryId.setBackgroundTintList(getColorStateList(R.color.colorAccent));
        btChooseBookAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUSET_CODE_GALLERY);
            }
        });
        btSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String book_id = edBookId.getText().toString().trim();
                String book_name = edBookTitle.getText().toString().trim();
                String book_description = edBookDescription.getText().toString().trim();
                String book_category_id = (String) spBookCategoryId.getSelectedItem();
                String book_author = edBookAuthor.getText().toString().trim();
                String book_publisher = edBookPublisher.getText().toString().trim();
                String book_price = edBookPrice.getText().toString().trim();
                String book_count = edBookCount.getText().toString().trim();
                if(book_id.equalsIgnoreCase("")){
                    edBookId.setError("Book ID do not empty!");
                }else if(book_name.equalsIgnoreCase("")){
                    edBookTitle.setError("Book Name do not empty!");
                }else if(book_author.equalsIgnoreCase("")){
                    edBookAuthor.setError("Book Author do not empty!");
                }else if(book_description.equalsIgnoreCase("")){
                    edBookDescription.setError("Book Description do not empty!");
                }else if(book_publisher.equalsIgnoreCase("")){
                    edBookPublisher.setError("Book Publisher do not empty!");
                }else if(book_price.equalsIgnoreCase("")){
                    edBookPrice.setError("Book Price do not empty!");
                }else if(book_count.equalsIgnoreCase("")){
                    edBookCount.setError("Book Count do not empty!");
                }else{
                    boolean check = true;
                    for(int i=0; i<daoBook.getAllBook().size(); i++){
                        if(book_id.equalsIgnoreCase(daoBook.getAllBook().get(i).getBookId())){
                            check = false;
                            break;
                        }else{
                            check = true;
                        }
                    }
                    if(check==true){
                        try{
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgBookAvatar.getDrawable();
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                            byte[] avatarBook = byteArray.toByteArray();
                            Book book = new Book(book_id, book_name, book_description, book_category_id,
                                    book_author, book_publisher, book_price, book_count, avatarBook);
                            daoBook.insertBook(book);
                            BookFragment.listBook = new ArrayList<>();
                            BookFragment.listBook.addAll(BookFragment.daoBook.getAllBook());
                            BookFragment.adapterBook = new AdapterBook(BookFragment.listBook);
                            BookFragment.rvBook.setAdapter(BookFragment.adapterBook);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AddBookActivity.this);
                            BookFragment.rvBook.setLayoutManager(layoutManager);
                            finish();
                        }catch (Exception e){
                            Toast.makeText(AddBookActivity.this, "Please select an image!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        edBookId.setError("Book ID already exits!");
                    }
                }
                
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgBookAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void initViews(){
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        edBookId = (EditText) findViewById(R.id.edBookId);
        edBookTitle = (EditText) findViewById(R.id.edBookTitle);
        spBookCategoryId = (Spinner) findViewById(R.id.spBookCategoryId);
        edBookAuthor = (EditText) findViewById(R.id.edBookAuthor);
        edBookPublisher = (EditText) findViewById(R.id.edBookPublisher);
        edBookDescription = (EditText) findViewById(R.id.edBookDescription);
        edBookPrice = (EditText) findViewById(R.id.edBookPrice);
        edBookCount = (EditText) findViewById(R.id.edBookCount);
        imgBookAvatar = (ImageView) findViewById(R.id.imgBookAvatar);
        btChooseBookAvatar = (Button) findViewById(R.id.btChooseBookAvatar);
        btSaveBook = (Button) findViewById(R.id.btSaveBook);
        btCancelBook = (Button) findViewById(R.id.btCancelBook);
        btShowBook = (Button) findViewById(R.id.btShowBook);
        databaseHelper = new DatabaseHelper(this);
        daoCategory = new DAOCategory(databaseHelper);
        daoBook = new DAOBook(databaseHelper);

    }
}
