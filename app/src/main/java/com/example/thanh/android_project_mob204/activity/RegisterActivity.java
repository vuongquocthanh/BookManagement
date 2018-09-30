package com.example.thanh.android_project_mob204.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.User;
import com.example.thanh.android_project_mob204.sqlitedao.UserDAO;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbarRegister;
    private EditText edRegisterUsername;
    private EditText edRegisterName;
    private EditText edRegisterPassword;
    private EditText edRegisterConfirmPassword;
    private EditText edPhoneNumber;
    private Button btRegister;
    private DatabaseHelper databaseHelper;

    private TextInputLayout tilRegistrationUsername,
            tilRegistrationName, tilRegistrationPassword,
            tilRegistrationConfirmPassword, tilRegistrationPhoneNumber;

    private final String pattern = ".{6,15}";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(this);
        final UserDAO userDAO = new UserDAO(databaseHelper);

        toolbarRegister = (Toolbar) findViewById(R.id.toolbarRegister);
        edRegisterUsername = (EditText) findViewById(R.id.edRegisterUsername);
        edRegisterName = (EditText) findViewById(R.id.edRegisterName);
        edRegisterPassword = (EditText) findViewById(R.id.edRegisterPassword);
        edRegisterConfirmPassword = (EditText) findViewById(R.id.edRegisterConfirmPassword);
        edPhoneNumber = (EditText) findViewById(R.id.edPhoneNumber);
        btRegister = (Button) findViewById(R.id.btRegister);

        tilRegistrationUsername = findViewById(R.id.tilRegistrationUsername);
        tilRegistrationName = findViewById(R.id.tilRegistrationName);
        tilRegistrationPassword = findViewById(R.id.tilRegistrationPassword);
        tilRegistrationConfirmPassword = findViewById(R.id.tilRegistrationConfirmPassword);
        tilRegistrationPhoneNumber = findViewById(R.id.tilRegistrationPhoneNumber);

        toolbarRegister.setNavigationIcon(R.drawable.ic_nav);
        toolbarRegister.setTitle("Registration");
        toolbarRegister.setTitleTextColor(getColor(R.color.colorAccent));
        setSupportActionBar(toolbarRegister);
        toolbarRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edRegisterUsername.getText().toString().trim();
                String name = edRegisterName.getText().toString().trim();
                String password = edRegisterPassword.getText().toString().trim();
                String confirmpassword = edRegisterConfirmPassword.getText().toString().trim();
                String phoneNumber = edPhoneNumber.getText().toString().trim();
                if(username.equalsIgnoreCase("")){
                    tilRegistrationUsername.setError(getString(R.string.notify_empty_username));
                }
                else if(name.equalsIgnoreCase("")){
                    tilRegistrationUsername.setErrorEnabled(false);
                    tilRegistrationName.setError(getString(R.string.notify_empty_name));
                }
                else if(password.equalsIgnoreCase("")){
                    tilRegistrationUsername.setErrorEnabled(false);
                    tilRegistrationName.setErrorEnabled(false);
                    tilRegistrationPassword.setError(getString(R.string.notify_empty_password));
                }
                else if(!(password.matches(pattern))){
                    tilRegistrationUsername.setErrorEnabled(false);
                    tilRegistrationName.setErrorEnabled(false);
                    tilRegistrationPassword.setError(getString(R.string.notify_error_password));
                }
                else if(confirmpassword.equalsIgnoreCase("") || !(confirmpassword.equalsIgnoreCase(password))){
                    tilRegistrationUsername.setErrorEnabled(false);
                    tilRegistrationName.setErrorEnabled(false);
                    tilRegistrationPassword.setErrorEnabled(false);
                    tilRegistrationConfirmPassword.setError(getString(R.string.notify_not_mathches_password));
                }
                else if(phoneNumber.equalsIgnoreCase("")){
                    tilRegistrationUsername.setErrorEnabled(false);
                    tilRegistrationName.setErrorEnabled(false);
                    tilRegistrationConfirmPassword.setErrorEnabled(false);
                    tilRegistrationPhoneNumber.setError(getString(R.string.notify_empty_phone));
                }else if(phoneNumber.length()<9 || phoneNumber.length() > 11){
                    tilRegistrationUsername.setErrorEnabled(false);
                    tilRegistrationName.setErrorEnabled(false);
                    tilRegistrationPassword.setErrorEnabled(false);
                    tilRegistrationConfirmPassword.setErrorEnabled(false);
                    tilRegistrationPhoneNumber.setError(getString(R.string.notify_min_length_phone));
                }
                else{
                    tilRegistrationUsername.setErrorEnabled(false);
                    tilRegistrationName.setErrorEnabled(false);
                    tilRegistrationPassword.setErrorEnabled(false);
                    tilRegistrationConfirmPassword.setErrorEnabled(false);
                    tilRegistrationPhoneNumber.setErrorEnabled(false);
                    boolean check = true;
                    for(int i=0; i<userDAO.getAllUser().size(); i++){
                        if(username.equalsIgnoreCase(userDAO.getAllUser().get(i).getUsername())){
                            check = false;
                            break;
                        }else{
                            check = true;
                        }
                    }
                    if(check==true){
                        userDAO.insertUser(new User(username, password, name, phoneNumber));
                        Toast.makeText(RegisterActivity.this, "Create acount successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, ListUser.class);
                        startActivity(intent);
                    }else{
                        tilRegistrationUsername.setErrorEnabled(true);
                        tilRegistrationUsername.setError(getString(R.string.notify_already_exists));
                    }
                }
            }
        });

    }
}
