package com.example.thanh.android_project_mob204.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.User;
import com.example.thanh.android_project_mob204.sqlitedao.UserDAO;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar toolbarChangepassword;
    private EditText edOldPassord, edNewPassword, edConfirmPassword;
    private Button btSave;
    private TextInputLayout tilOldPassword, tilNewPassword, tilConfirmPassword;
    private DatabaseHelper databaseHelper;
    private final String pattern = ".{6,15}";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        databaseHelper = new DatabaseHelper(this);
        final UserDAO userDAO = new UserDAO(databaseHelper);

//        Lấy dữ liệu từ MoreFragment
        final Intent intentGetActivity = getIntent();
        final String usernameLogin = intentGetActivity.getStringExtra("usernameFromMoreFragment");
        Log.e("AlertUserName", intentGetActivity.getStringExtra("usernameFromMoreFragment"));

        toolbarChangepassword = findViewById(R.id.toolbarChangePassword);
        toolbarChangepassword.setTitle("Change Password");
        toolbarChangepassword.setTitleTextColor(getColor(R.color.colorAccent));
        toolbarChangepassword.setNavigationIcon(R.drawable.ic_nav);
        setSupportActionBar(toolbarChangepassword);
        toolbarChangepassword.setTitleMarginStart(30);
        toolbarChangepassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edOldPassord = findViewById(R.id.edOldPassword);
        edNewPassword = findViewById(R.id.edNewPassword);
        edConfirmPassword = findViewById(R.id.edRetype);
        tilOldPassword = findViewById(R.id.tilOldPassword);
        tilNewPassword = findViewById(R.id.tilNewPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpassword = edOldPassord.getText().toString().trim();
                String newpassword = edNewPassword.getText().toString().trim();
                String confirmpassword = edConfirmPassword.getText().toString().trim();
                if (oldpassword.equalsIgnoreCase("") || newpassword.equalsIgnoreCase("") || confirmpassword.equalsIgnoreCase("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Please complete all infomation!", Toast.LENGTH_SHORT).show();
                } else {
                    if (!oldpassword.equalsIgnoreCase(userDAO.getUser(usernameLogin).getPassword())) {
                        tilOldPassword.setError(getString(R.string.notify_error_oldpassword));
                    } else if (!(newpassword.matches(pattern))) {
                        tilOldPassword.setErrorEnabled(false);
                        tilNewPassword.setError(getString(R.string.notify_error_password));
                    } else if (!(confirmpassword.equalsIgnoreCase(newpassword))) {
                        tilNewPassword.setErrorEnabled(false);
                        tilOldPassword.setErrorEnabled(false);
                        tilConfirmPassword.setError(getString(R.string.notify_error_confirmpassword));
                    } else {
                        tilNewPassword.setErrorEnabled(false);
                        tilOldPassword.setErrorEnabled(false);
                        tilConfirmPassword.setErrorEnabled(false);
                        userDAO.updateUser(new User(usernameLogin, confirmpassword, userDAO.getUser(usernameLogin).getName(), userDAO.getUser(usernameLogin).getPhonenumber()));
                        Toast.makeText(ChangePasswordActivity.this, "Change password successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
