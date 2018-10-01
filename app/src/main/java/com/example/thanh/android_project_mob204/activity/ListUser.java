package com.example.thanh.android_project_mob204.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.UserAdapter;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.interfaces.OnItemDeleteListener;
import com.example.thanh.android_project_mob204.model.User;
import com.example.thanh.android_project_mob204.sqlitedao.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class ListUser extends AppCompatActivity {
    private UserAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<User> listUser;
    private ListView lvUser;
    private Toolbar toolbar;
    private UserDAO userDAO;
    private Button btFindUser;
    private EditText edEnterName;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        btFindUser = findViewById(R.id.btFindUser);
        edEnterName = findViewById(R.id.edEnterName);
        toolbar = findViewById(R.id.toolbarListUser);
        toolbar.setTitle("List User");
        toolbar.setTitleTextColor(getColor(R.color.colorAccent));
        toolbar.setNavigationIcon(R.drawable.ic_nav);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvUser = findViewById(R.id.lvUser);
        initList();
        search();
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ListUser.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                builder.setTitle("Edit User");
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.dialog_edit_user, null);
                builder.setView(viewDialog);

                final EditText edUserName = viewDialog.findViewById(R.id.edRegisterUsername);
                final EditText name = viewDialog.findViewById(R.id.edRegisterName);
                final EditText phone = viewDialog.findViewById(R.id.edPhoneNumber);
                final TextInputLayout tilEditUserName = viewDialog.findViewById(R.id.tilEditUserName);
                TextInputLayout tilEditName = viewDialog.findViewById(R.id.tilEditName);
                TextInputLayout tilEditPhoneNumber = viewDialog.findViewById(R.id.tilEditPhoneNumber);

                edUserName.setText(listUser.get(position).getUsername());
                name.setText(listUser.get(position).getName());
                phone.setText(listUser.get(position).getPhonenumber());
                builder.setNegativeButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nameEdit = name.getText().toString();
                        String usernameEdit = edUserName.getText().toString();
                        String phoneEdit = phone.getText().toString();
                        if (nameEdit.equalsIgnoreCase("")) {
                            Toast.makeText(ListUser.this, getString(R.string.notify_empty_username), Toast.LENGTH_SHORT).show();
//                            tilEditUserName.setError(getString(R.string.notify_empty_username));
                        }
                        else if (usernameEdit.equalsIgnoreCase("")) {
                            Toast.makeText(ListUser.this, getString(R.string.notify_empty_username), Toast.LENGTH_SHORT).show();
//                            edUserName.setError(getString(R.string.notify_empty_username));
                        }
                        else if (phoneEdit.equalsIgnoreCase("")) {
                            Toast.makeText(ListUser.this, getString(R.string.notify_empty_phone), Toast.LENGTH_SHORT).show();
//                            phone.setError(getString(R.string.notify_empty_phone));
                        } else if (phoneEdit.length() < 9 || phoneEdit.length()>11) {
                            Toast.makeText(ListUser.this, getString(R.string.notify_min_length_phone), Toast.LENGTH_SHORT).show();
//                            phone.setError(getString(R.string.notify_min_length_phone));
                        }else{
                            listUser.get(position).setName(nameEdit);
                            listUser.get(position).setUsername(usernameEdit);
                            listUser.get(position).setPhonenumber(phoneEdit);
                            userDAO.updateUser(new User(usernameEdit, listUser.get(position).getPassword(), nameEdit, phoneEdit));
                            initList();
                        }
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });

    }

    public void initList(){
        databaseHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(databaseHelper);
        listUser = new ArrayList<>();
        listUser.addAll(userDAO.getAllUser());
        adapter = new UserAdapter(ListUser.this, R.layout.item_user, listUser, new OnItemDeleteListener() {
            @Override
            public void OnItemDeleted(int position) {
                userDAO.deleteUser(listUser.get(position));
                listUser.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        lvUser.setAdapter(adapter);
    }
    public void search(){
        edEnterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equalsIgnoreCase("")){
//                    Reset ListView
                    initList();
                }else{
                    searchItem(s.toString());
                    Log.e("size", String.valueOf(listUser.size()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void searchItem(String textToSearch){
        for(int i=0; i<listUser.size(); i++){
            if(!(listUser.get(i).getName().toLowerCase().contains(textToSearch.toLowerCase()))){
                listUser.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
