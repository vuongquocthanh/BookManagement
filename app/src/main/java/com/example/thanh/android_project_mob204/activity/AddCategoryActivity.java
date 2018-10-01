package com.example.thanh.android_project_mob204.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.AdapterCategory;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.fragment.LibraryFragment;
import com.example.thanh.android_project_mob204.model.Category;
import com.example.thanh.android_project_mob204.model.Library;
import com.example.thanh.android_project_mob204.sqlitedao.DAOCategory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddCategoryActivity extends AppCompatActivity {

    private Toolbar toolbarAddCategory;
    private TextInputLayout tilCategoryId;
    private EditText edCategoryId;
    private TextInputLayout tilCategoryName;
    private EditText edCategoryName;
    private TextInputLayout tilCategoryDescription;
    private EditText edCategoryDescription;
    private TextInputLayout tilCategoryPosition;
    private EditText edCategoryPosition;
    private ImageView imgCategoryAvatar;
    private Button btChooseImageCategory;
    private Button btCategorySave;
    private Button btCategoryCancel;
    private int REQUEST_CODE_GALLERY = 123;
    private DatabaseHelper databaseHelper;
    private DAOCategory daoCategory;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        initViews();
        toolbarAddCategory.setNavigationIcon(R.drawable.ic_nav);
        toolbarAddCategory.setTitle("Add Category");
        toolbarAddCategory.setTitleTextColor(getColor(R.color.colorAccent));
        setSupportActionBar(toolbarAddCategory);
        toolbarAddCategory.setTitleMarginStart(30);
        toolbarAddCategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btChooseImageCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        });
        btCategorySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category_id = edCategoryId.getText().toString().trim();
                String category_name = edCategoryName.getText().toString().trim();
                String category_description = edCategoryDescription.getText().toString().trim();
                String category_position = edCategoryPosition.getText().toString().trim();
                if (category_id.equalsIgnoreCase("")) {
                    tilCategoryId.setErrorEnabled(false);
                    tilCategoryName.setErrorEnabled(false);
                    tilCategoryDescription.setErrorEnabled(false);
                    tilCategoryId.setError(getString(R.string.notify_empty_category_id));
                }else if(category_id.length()>5){
                    tilCategoryId.setErrorEnabled(false);
                    tilCategoryName.setErrorEnabled(false);
                    tilCategoryDescription.setErrorEnabled(false);
                    tilCategoryId.setError(getString(R.string.notify_error_category_id_length));
                }
                else if (category_name.equalsIgnoreCase("")) {
                    tilCategoryId.setErrorEnabled(false);
                    tilCategoryName.setErrorEnabled(false);
                    tilCategoryDescription.setErrorEnabled(false);
                    tilCategoryName.setError(getString(R.string.notify_empty_category_name));
                } else if (category_description.equalsIgnoreCase("")) {
                    tilCategoryId.setErrorEnabled(false);
                    tilCategoryName.setErrorEnabled(false);
                    tilCategoryDescription.setErrorEnabled(false);
                    tilCategoryDescription.setError(getString(R.string.notify_empty_category_description));
                } else if (category_position.equalsIgnoreCase("")) {
                    tilCategoryId.setErrorEnabled(false);
                    tilCategoryName.setErrorEnabled(false);
                    tilCategoryDescription.setErrorEnabled(false);
                    tilCategoryPosition.setError(getString(R.string.notify_empty_category_position));
                } else {
                    boolean check = true;
                    for(int i=0; i<daoCategory.getAllCategory().size(); i++){
                        if(category_id.equalsIgnoreCase(daoCategory.getAllCategory().get(i).getCategoryID())){
                            check = false;
                            break;
                        }else{
                            check = true;
                        }
                    }
                    if (check==true){
                        try {
                            //                Chuyển ImageView => byte[]
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgCategoryAvatar.getDrawable();
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                            byte[] avatarCategory = byteArray.toByteArray();
                            Category category = new Category(category_id, category_name, avatarCategory, category_description, category_position);
                            daoCategory.insertCategory(category);
                            Toast.makeText(AddCategoryActivity.this, "Add category successfully!!", Toast.LENGTH_SHORT).show();
                            LibraryFragment.listLibrary = new ArrayList<>();
                            LibraryFragment.listLibrary.addAll(LibraryFragment.daoCategory.getAllCategory());
                            LibraryFragment.adapter = new AdapterCategory(LibraryFragment.listLibrary);
                            LibraryFragment.rvLibrary.setAdapter(LibraryFragment.adapter);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(AddCategoryActivity.this, 2);
                            LibraryFragment.rvLibrary.setLayoutManager(layoutManager);
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(AddCategoryActivity.this, "Please select an image!!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        tilCategoryId.setErrorEnabled(true);
                        tilCategoryId.setError(getString(R.string.notify_error_category_id));
                    }

                }

            }
        });

        btCategoryCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgCategoryAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void initViews() {
        toolbarAddCategory = (Toolbar) findViewById(R.id.toolbarAddCategory);
        tilCategoryId = (TextInputLayout) findViewById(R.id.tilCategoryId);
        edCategoryId = (EditText) findViewById(R.id.edCategoryId);
        tilCategoryName = (TextInputLayout) findViewById(R.id.tilCategoryName);
        edCategoryName = (EditText) findViewById(R.id.edCategoryName);
        tilCategoryDescription = (TextInputLayout) findViewById(R.id.tilCategoryDescription);
        edCategoryDescription = (EditText) findViewById(R.id.edCategoryDescription);
        tilCategoryPosition = (TextInputLayout) findViewById(R.id.tilCategoryPosition);
        edCategoryPosition = (EditText) findViewById(R.id.edCategoryPosition);
        imgCategoryAvatar = (ImageView) findViewById(R.id.imgCategoryAvatar);
        btChooseImageCategory = (Button) findViewById(R.id.btChooseImageCategory);
        btCategorySave = (Button) findViewById(R.id.btCategorySave);
        btCategoryCancel = (Button) findViewById(R.id.btCategoryCancel);
        databaseHelper = new DatabaseHelper(this);
        daoCategory = new DAOCategory(databaseHelper);

    }

    public void initData() {
        String category_id = edCategoryId.getText().toString().trim();
        String category_name = edCategoryName.getText().toString().trim();
        String category_description = edCategoryDescription.getText().toString().trim();
        String category_position = edCategoryPosition.getText().toString().trim();
    }
}
