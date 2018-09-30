package com.example.thanh.android_project_mob204.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.fragment.BookFragment;
import com.example.thanh.android_project_mob204.fragment.MoreFragment;
import com.example.thanh.android_project_mob204.model.User;
import com.example.thanh.android_project_mob204.sqlitedao.UserDAO;

public class LoginActivity extends AppCompatActivity {
    private EditText edUsername, edPassword;
    private final String pattern = ".{6,15}";
    private CheckBox cbRemember;
    private DatabaseHelper databaseHelper;
    private UserDAO userDAO;
    private MoreFragment moreFragment;
    private BookFragment bookFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        cbRemember = findViewById(R.id.cbRemember);
        restoringPreferences();
        databaseHelper = new DatabaseHelper(this);
        userDAO = new UserDAO(databaseHelper);
//        userDAO.insertUser(new User("admin", "admin123", "Vuong Quoc Thanh", "01685150619"));
        bookFragment = new BookFragment();
    }

    public void showBottomNavigation(View view) {
        String username = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString().trim();



        if(username.equalsIgnoreCase("")){
            edUsername.setError(getString(R.string.notify_empty_username));
        }
        if(password.equalsIgnoreCase("")){
            edPassword.setError(getString(R.string.notify_empty_password));
        }else if(!(password.matches(pattern))){
            edPassword.setError(getString(R.string.notify_error_password));
        }else{
            savingPreferences();
            UserDAO userDAO = new UserDAO(databaseHelper);
            User user = userDAO.getUser(username);
            if(user!=null){
                String originalPassword = user.getPassword();
                if(password.equalsIgnoreCase(originalPassword)){

                    Intent intent = new Intent(LoginActivity.this, BottomNavigationBarActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);

                }else{
                    Toast.makeText(this, getString(R.string.notify_wrong_username_password), Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, getString(R.string.notify_wrong_username_password), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void showRegistration(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void savingPreferences(){
        SharedPreferences sharedPreference = getSharedPreferences("filename", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();
        String username = edUsername.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        boolean chk = cbRemember.isChecked();
        if(!chk){
            editor.clear();
        }else{
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("savestatus", chk);
        }
        editor.commit();
    }

    private void restoringPreferences(){
        SharedPreferences pref = getSharedPreferences("filename", MODE_PRIVATE);
        boolean chk = pref.getBoolean("savestatus", false);
        if(chk){
            String user = pref.getString("username", "");
            String pass = pref.getString("password", "");
            edUsername.setText(user);
            edPassword.setText(pass);
        }
        cbRemember.setChecked(chk);
    }
}
